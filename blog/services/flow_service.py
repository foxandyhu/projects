from models.system_model import SysAccess, SysAccessPage, SysAccessStatistic, SysAccessCount, SysAccessCountHour
from flask import session
from utils import context_utils, date_utils
from datetime import datetime
from extensions import cache, db, logger
import itertools, re
import threading
from sqlalchemy import inspect


class FlowService(object):
    """系统流量业务接口"""

    CACHE_TIMEOUT_ADAY = 24 * 60 * 60  # 缓存超时时间
    ACCESS_COUNT_SESSION_FLAG = "access_count_flag"  # 每个客户端访问的次数统计标识
    ACCESS_LAST_TIME = "access_last_time"  # 站点最后一次的访问时间
    ACCESS_CACHE_SITE_FLAG, SESSION_SITE_NAMESPACE = "_site_", "SESSION_SITE:"  # session访问站点记录 缓存标识
    ACCESS_CACHE_PAGE_FLAG, SESSION_PAGE_NAMESPACE = "_page_", "SESSION_PAGE:"  # 页面访问记录 缓存标识
    CACHE_FRESH_DB_TIME = datetime.now()  # 缓存中的数据写入数据库的时间标识（10分钟写入访问统计数据到数据库）

    @staticmethod
    def get_cache_site_namespace(session_id):
        """每个session的站点访问缓存标识"""

        return f"{FlowService.SESSION_SITE_NAMESPACE}{session_id}{FlowService.ACCESS_CACHE_SITE_FLAG}"

    @staticmethod
    def get_cache_page_namespace(session_id, access_count):
        """每个session的页面访问缓存标识"""

        return f"{FlowService.SESSION_PAGE_NAMESPACE}{session_id}{FlowService.ACCESS_CACHE_PAGE_FLAG}{access_count}"

    @staticmethod
    def flow_statistic(request, page, referrer):
        ip = context_utils.get_client_request_ip(request)
        brower = context_utils.get_client_request_brower(request)
        os = context_utils.get_client_request_os(request)

        now, session_id = datetime.now(), session.sid

        access_count = session.get(FlowService.ACCESS_COUNT_SESSION_FLAG) or 0
        if access_count == 0:  # 新客户第一次访问
            access = FlowService.wrap_access(request, session_id, ip, page, referrer, brower, os, now)
            access.entry_page = page
        else:
            access = FlowService.find_access(session_id)
            if access is None:  # 防止缓存和数据库中数据丢失
                access = FlowService.wrap_access(request, session_id, ip, page, referrer, brower, os, now)

        access_count += 1
        access_last_time = datetime.combine(access.access_date, access.access_time)
        access.stop_page = page
        access.visit_page_count = access_count  # 更新访问次数和最后一次访问时间
        access.visit_second = (now - access_last_time).seconds

        access_page = FlowService.wrap_access_page(session_id, page, access_count, now)

        session[FlowService.ACCESS_COUNT_SESSION_FLAG] = access.visit_page_count

        cache.set(FlowService.get_cache_site_namespace(session_id), access,
                  FlowService.CACHE_TIMEOUT_ADAY)  # 把每个session的访问信息放入到缓存中，统一处理
        cache.set(FlowService.get_cache_page_namespace(session_id, access_count), access_page,
                  FlowService.CACHE_TIMEOUT_ADAY)

        if (now - FlowService.CACHE_FRESH_DB_TIME).seconds > 10 * 60:  # 每隔10分钟刷新缓存数据到数据库
            FlowService.CACHE_FRESH_DB_TIME = now
            FlowService.flush_cache_to_db()

        site_last_time = cache.get(FlowService.ACCESS_LAST_TIME)
        if site_last_time:  # 每天第一个访问标识统计昨日的流量
            site_last_time = date_utils.parse_str_datetime(site_last_time)
            if site_last_time.date() < now.date():
                context = db.get_app().app_context()
                statistic_thread = FlowStatisticThread(context)
                statistic_thread.start()

        cache.set(FlowService.ACCESS_LAST_TIME, date_utils.parse_datetime_str(now), 0)  # 重新设置站点最后的访问时间

    @staticmethod
    def wrap_access(request, session_id, ip, stop_page, referrer, brower, os, date_time):
        """包装流量访问对象"""

        access = SysAccess()
        access.session_id = session_id
        access.browser = brower
        access.operating_system = os
        access.access_ip = ip
        access.stop_page = stop_page
        access.access_date = date_time.date()
        access.access_time = date_time.time()
        FlowService.analysis_source(request, access, referrer)
        return access

    @staticmethod
    def analysis_source(request, access, referrer):
        """分析链接的来源"""

        if not referrer:
            access.access_source = "direct_access"
            return access

        engines = ("baidu.", "google.", "yahoo.", "bing.", "bing.", "soso.", "so.")
        name = tuple(itertools.filterfalse(lambda engine: referrer.find(engine) == -1, engines))
        if len(name) > 0:  # 来自搜索引擎
            access.engine = name[0]
            access.access_source = "engine_access"

            pattern = "(?:yahoo.+?[\\?|&]q=|openfind.+?q=|google.+?q=|lycos.+?query=|onseek.+?keyword=|search\\.tom.+?word=|search\\.qq\\.com.+?word=|zhongsou\\.com.+?word=|search\\.msn\\.com.+?q=|yisou\\.com.+?p=|sina.+?word=|sina.+?query=|sina.+?_searchkey=|sohu.+?word=|sohu.+?key_word=|sohu.+?query=|163.+?q=|baidu.+?wd=|soso.+?w=|3721\\.com.+?p=|Alltheweb.+?q=)([^&]*)"
            if re.match(pattern, referrer):
                access.keyword = re.findall(pattern, referrer)[0]
        else:
            start, index = 0, 0
            while start != -1:
                start = referrer.find("/", start + 1)
                if start != -1:
                    index = start
            referrer = referrer[0:index]
            access.external_link = referrer

            sysinfo = cache.get("sysinfo")
            host = None
            if sysinfo:
                host = sysinfo.website
            if host and referrer.find(host) < 0:
                access.access_source = "external_access"
            else:
                access.access_source = "direct_access"
        return access

    @staticmethod
    def find_access(session_id):
        """根据session_id 查找访问对象"""

        access = cache.get(FlowService.get_cache_site_namespace(session_id))
        if access is None:  # 如果缓存中不存在则从数据库中查找
            access = SysAccess.query.filter(SysAccess.session_id == session_id).first()
        return access

    @staticmethod
    def wrap_access_page(session_id, page, access_count, date_time):
        """包装访问页对象"""
        sys_page = SysAccessPage()
        sys_page.session_id = session_id
        sys_page.access_page = page
        sys_page.access_time = date_time.time()
        sys_page.access_date = date_time.date()
        sys_page.visit_second = 0
        sys_page.seq = access_count

        pre_cache_key = FlowService.get_cache_page_namespace(session_id, access_count - 1)
        pre_page = cache.get(pre_cache_key)
        if pre_page is None:
            pre_page = SysAccessPage.query.filter(
                SysAccessPage.session_id == session_id).filter(SysAccessPage.seq == access_count - 1).first()
        if pre_page:
            seconds = (date_time - datetime.combine(pre_page.access_date, pre_page.access_time)).seconds
            pre_page.visit_second = seconds
            cache.set(pre_cache_key, pre_page, FlowService.CACHE_TIMEOUT_ADAY)
        return sys_page

    @staticmethod
    def flush_cache_to_db():
        """把缓存数据写入数据库"""

        site_keys = FlowService.flush_session_access_to_db()
        page_keys = FlowService.flush_session_pages_to_db()

        cache._client.delete(*site_keys)
        cache._client.delete(*page_keys)

    @staticmethod
    def flush_session_pages_to_db():
        """把每个session访问的详细页面写入数据库"""

        result = cache._client.scan_iter(match=f"{cache.key_prefix}{FlowService.SESSION_PAGE_NAMESPACE}*")
        keys = list()
        for key in result:
            key = key.decode()
            keys.append(key)
            page = cache._client.get(key)
            if page:
                page = cache.load_object(page)
                FlowService.merge_page(page)
        return keys

    @staticmethod
    def flush_session_access_to_db():
        """每个session访问站点数据缓存写入数据库"""

        result = cache._client.scan_iter(match=f"{cache.key_prefix}{FlowService.SESSION_SITE_NAMESPACE}*")
        keys = list()
        for key in result:
            key = key.decode()
            keys.append(key)
            access = cache._client.get(key)
            if access:
                access = cache.load_object(access)
                FlowService.merge_access(access)
        return keys

    @staticmethod
    def merge_page(page):
        """新增或修改SysAccessPage"""

        if not page.id:
            db.session.add(page)
        else:
            ins = inspect(page)
            if not ins.persistent:
                db.session.merge(page)
        db.session.commit()

    @staticmethod
    def merge_access(access):
        """新增或修改SysAccess"""

        if not access.id:
            db.session.add(access)
        else:
            ins = inspect(access)
            if not ins.persistent:
                db.session.merge(access)
        db.session.commit()


class FlowStatisticService(object):
    """流量统计服务类"""

    @staticmethod
    def get_statistic_pre_days(date):
        """查找访问日期是否存在之前日期 避免之前日期数据统计丢失"""

        result = db.session.query(db.distinct(SysAccess.access_date)).filter(SysAccess.access_date < date).all()
        return result

    @staticmethod
    def add_statistic(statistic_date, pv, ip, visitors, pages_aver, visit_second_aver, statistic_key,
                      statistic_value=None):
        """保存统计的流量数据"""

        statistic = SysAccessStatistic()
        statistic.statistic_date = statistic_date
        statistic.pv = pv
        statistic.ip = ip
        statistic.visitors = visitors
        statistic.pages_aver = pages_aver
        statistic.visit_second_aver = visit_second_aver
        statistic.statistic_key = statistic_key
        statistic.statistic_value = statistic_value

        db.session.add(statistic)
        db.session.commit()

    @staticmethod
    def statistic_day(date):
        """根据指定日期统计PV，UV，IP等汇总信息"""
        result = db.session.query(db.func.count(db.distinct(SysAccess.access_ip)).label("IP"),
                                  db.func.count(db.distinct(SysAccess.session_id)).label("UV"),
                                  db.func.sum(SysAccess.visit_page_count).label("PV"),
                                  db.func.sum(SysAccess.visit_second).label("ST"), db.func.count(1)).filter(
            SysAccess.access_date == date).all()
        return result

    @staticmethod
    def statistic_area(date):
        """在指定的日期根据访问地区统计"""

        result = db.session.query(db.func.count(db.distinct(SysAccess.access_ip)).label("IP"),
                                  db.func.count(db.distinct(SysAccess.session_id)).label("UV"),
                                  db.func.sum(SysAccess.visit_page_count).label("PV"),
                                  db.func.sum(SysAccess.visit_second).label("ST"), SysAccess.access_area).filter(
            SysAccess.access_date == date).filter(SysAccess.access_area != "").group_by(SysAccess.access_area).all()
        return result

    @staticmethod
    def statistic_source(date):
        """在指定的日期根据访问来源统计"""

        result = db.session.query(db.func.count(db.distinct(SysAccess.access_ip)).label("IP"),
                                  db.func.count(db.distinct(SysAccess.session_id)).label("UV"),
                                  db.func.sum(SysAccess.visit_page_count).label("PV"),
                                  db.func.sum(SysAccess.visit_second).label("ST"), SysAccess.access_source).filter(
            SysAccess.access_date == date).filter(SysAccess.access_source != "").group_by(SysAccess.access_source).all()
        return result

    @staticmethod
    def statistic_engine(date):
        """在指定的日期根据搜索引擎统计"""

        result = db.session.query(db.func.count(db.distinct(SysAccess.access_ip)).label("IP"),
                                  db.func.count(db.distinct(SysAccess.session_id)).label("UV"),
                                  db.func.sum(SysAccess.visit_page_count).label("PV"),
                                  db.func.sum(SysAccess.visit_second).label("ST"), SysAccess.engine).filter(
            SysAccess.access_date == date).filter(SysAccess.engine != "").group_by(SysAccess.engine).all()
        return result

    @staticmethod
    def statistic_link(date):
        """在指定的日期根据外部链接统计"""

        result = db.session.query(db.func.count(db.distinct(SysAccess.access_ip)).label("IP"),
                                  db.func.count(db.distinct(SysAccess.session_id)).label("UV"),
                                  db.func.sum(SysAccess.visit_page_count).label("PV"),
                                  db.func.sum(SysAccess.visit_second).label("ST"), SysAccess.external_link).filter(
            SysAccess.access_date == date).filter(SysAccess.external_link != "").group_by(SysAccess.external_link).all()
        return result

    @staticmethod
    def statistic_keyword(date):
        """在指定的日期根据关键字统计"""

        result = db.session.query(db.func.count(db.distinct(SysAccess.access_ip)).label("IP"),
                                  db.func.count(db.distinct(SysAccess.session_id)).label("UV"),
                                  db.func.sum(SysAccess.visit_page_count).label("PV"),
                                  db.func.sum(SysAccess.visit_second).label("ST"), SysAccess.keyword).filter(
            SysAccess.access_date == date).filter(SysAccess.keyword != "").group_by(SysAccess.keyword).all()
        return result

    @staticmethod
    def statistic_brower(date):
        """在指定的日期根据浏览器统计"""

        result = db.session.query(db.func.count(db.distinct(SysAccess.access_ip)).label("IP"),
                                  db.func.count(db.distinct(SysAccess.session_id)).label("UV"),
                                  db.func.sum(SysAccess.visit_page_count).label("PV"),
                                  db.func.sum(SysAccess.visit_second).label("ST"),
                                  db.func.substring_index(SysAccess.browser, ":", 1).label("brower")).filter(
            SysAccess.access_date == date).filter(SysAccess.browser != "").group_by("brower").all()
        return result

    @staticmethod
    def statictis_page_count(date):
        """统计指定日期的页面访问量和访客总量"""

        result = db.session.query(db.func.count(db.distinct(SysAccess.session_id)).label("UV"),
                                  db.func.sum(SysAccess.visit_page_count).label("PV")).filter(
            SysAccess.access_date == date).first()

        access_count = SysAccessCount()
        access_count.visitors = result[0]
        access_count.page_count = result[1]
        access_count.statistic_date = date

        db.session.add(access_count)
        db.session.commit()

    @staticmethod
    def statistis_hour(date):
        """根据指定日期的小时段统计"""

        results = db.session.query(db.func.count(db.distinct(SysAccess.access_ip)).label("IP"),
                                   db.func.count(db.distinct(SysAccess.session_id)).label("UV"),
                                   db.func.sum(SysAccess.visit_page_count).label("PV"),
                                   db.func.hour(SysAccess.access_time).label("hour")).filter(
            SysAccess.access_date == date).group_by("hour").all()

        for result in results:
            access_hour = SysAccessCountHour()
            access_hour.hour_ip = result[0]
            access_hour.hour_uv = result[1]
            access_hour.hour_pv = result[2]
            access_hour.statistic_date = date
            access_hour.statistic_time = result[3]

            db.session.add(access_hour)
            db.session.commit()


class FlowStatisticThread(threading.Thread):
    """流量统计线程类"""
    STATISTIC_ALL, STATISTIC_SOURCE, STATISTIC_ENGINE, STATISTIC_LINK, STATISTIC_KEYWORD, STATISTIC_AREA, STATISTIC_BROWER = "all", "source", "engine", "link", "keyword", "area", "brower"

    def __init__(self, context):
        self.context = context
        super().__init__()

    def run(self):

        logger.info("开始统计今日之前的数据!")
        date = datetime.now().date()
        with self.context:
            days = FlowStatisticService.get_statistic_pre_days(date)
            for day in days:
                self.statistic_key(day[0], self.STATISTIC_ALL)
                self.statistic_key(day[0], self.STATISTIC_SOURCE)
                self.statistic_key(day[0], self.STATISTIC_ENGINE)
                self.statistic_key(day[0], self.STATISTIC_LINK)
                self.statistic_key(day[0], self.STATISTIC_KEYWORD)
                self.statistic_key(day[0], self.STATISTIC_AREA)
                self.statistic_key(day[0], self.STATISTIC_BROWER)

                FlowStatisticService.statictis_page_count(day[0])
                FlowStatisticService.statistis_hour(day[0])

                self.clear_access(day[0])
                self.clear_access_page(day[0])

    def statistic_key(self, date, key):
        """根据不同维度统计数据"""

        if key == self.STATISTIC_ALL:
            results = FlowStatisticService.statistic_day(date)
        elif key == self.STATISTIC_AREA:
            results = FlowStatisticService.statistic_area(date)
        elif key == self.STATISTIC_SOURCE:
            results = FlowStatisticService.statistic_source(date)
        elif key == self.STATISTIC_ENGINE:
            results = FlowStatisticService.statistic_engine(date)
        elif key == self.STATISTIC_LINK:
            results = FlowStatisticService.statistic_link(date)
        elif key == self.STATISTIC_KEYWORD:
            results = FlowStatisticService.statistic_keyword(date)
        elif key == self.STATISTIC_BROWER:
            results = FlowStatisticService.statistic_brower(date)

        for result in results:
            ip = result[0]
            visitors = result[1]
            pv = result[2]
            visit_second_aver = int(result[3] / visitors)
            pages_aver = int(pv / visitors)
            statistic_key = key
            statistic_value = result[4]
            FlowStatisticService.add_statistic(date, pv, ip, visitors, pages_aver, visit_second_aver, statistic_key,
                                               statistic_value)

    def clear_access(self, date):
        """清除指定日期的访问记录"""

        db.session.query(SysAccess).filter(SysAccess.access_date == date).delete()
        db.session.commit()

    def clear_access_page(self, date):
        """清除指定日期的页面访问记录"""

        db.session.query(SysAccessPage).filter(SysAccessPage.access_date == date).delete()
        db.session.commit()


class FlowReportService(object):
    """流量报表统计展示服务类"""

    @staticmethod
    def statistic_flow_today():
        """统计今日的总流量汇总数据"""

        date = datetime.now().date()
        results = db.session.query(db.func.count(db.distinct(SysAccess.access_ip)).label("IP"),
                                   db.func.count(db.distinct(SysAccess.session_id)).label("UV"),
                                   db.func.cast(db.func.sum(SysAccess.visit_page_count), db.Integer).label("PV"),
                                   db.func.date_format(SysAccess.access_time, "%H:00-%H:59").label("hour")).filter(
            SysAccess.access_date == date).group_by("hour").all()
        return results

    @staticmethod
    def statistic_source_type(begin, end, key_word="source", limit=-1):
        """根据指定类型来源统计"""

        query = db.session.query(db.func.cast(db.func.sum(SysAccessStatistic.ip), db.Integer).label("IP"),
                                 db.func.cast(db.func.count(SysAccessStatistic.visitors), db.Integer).label("UV"),
                                 db.func.cast(db.func.sum(SysAccessStatistic.pv), db.Integer).label("PV"),
                                 SysAccessStatistic.statistic_value).filter(
            SysAccessStatistic.statistic_date <= end).filter(SysAccessStatistic.statistic_date >= begin).filter(
            SysAccessStatistic.statistic_key == key_word).group_by(
            SysAccessStatistic.statistic_value)
        if limit > 0:
            query = query.limit(limit)
        results = query.all()
        return results

    @staticmethod
    def statistic_source_date(begin, end, key_word="source"):
        """在指定的日期根据访问来源统计"""

        query = db.session.query(db.func.cast(db.func.sum(SysAccessStatistic.ip), db.Integer).label("IP"),
                                 db.func.cast(db.func.count(SysAccessStatistic.visitors), db.Integer).label("UV"),
                                 db.func.cast(db.func.sum(SysAccessStatistic.pv), db.Integer).label("PV"),
                                 SysAccessStatistic.statistic_value,
                                 db.func.date_format(SysAccessStatistic.statistic_date, "%Y-%m-%d")).filter(
            SysAccessStatistic.statistic_date <= end).filter(SysAccessStatistic.statistic_date >= begin).filter(
            SysAccessStatistic.statistic_key == key_word).group_by(SysAccessStatistic.statistic_value).group_by(
            SysAccessStatistic.statistic_date)
        results = query.all()
        return results
