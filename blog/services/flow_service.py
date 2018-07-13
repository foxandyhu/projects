from models.system_model import SysAccess, SysAccessPage
from flask import session
from utils import context_utils, date_utils
from datetime import datetime, timedelta
from extensions import cache, db
import itertools, re
import threading
from sqlalchemy import inspect


class FlowService(object):
    """系统流量业务接口"""

    CACHE_TIMEOUT_ADAY = 24 * 60 * 60  # 缓存超时时间
    ACCESS_COUNT_SESSION_FLAG = "access_count_flag"  # 每个客户端访问的次数统计标识
    ACCESS_LAST_TIME = "access_last_time"  # 网站最后一次的访问时间
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

        access_last_time = cache.get(FlowService.ACCESS_LAST_TIME)
        is_new_day = False  # 每天第一个访问标识统计昨日的流量
        if access_last_time:
            access_last_time = date_utils.parse_str_datetime(access_last_time)
            if access_last_time.date() < now.date():
                is_new_day = True

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

        cache.set(FlowService.ACCESS_LAST_TIME, date_utils.parse_datetime_str(now), 0)  # 重新设置站点的访问时间
        cache.set(FlowService.get_cache_site_namespace(session_id), access,
                  FlowService.CACHE_TIMEOUT_ADAY)  # 把每个session的访问信息放入到缓存中，统一处理
        cache.set(FlowService.get_cache_page_namespace(session_id, access_count), access_page,
                  FlowService.CACHE_TIMEOUT_ADAY)

        if (now - FlowService.CACHE_FRESH_DB_TIME).seconds > 10 * 60:
            FlowService.CACHE_FRESH_DB_TIME = now
            FlowService.flush_cache_to_db()

        if is_new_day:  # 统计昨日的流量
            statistic_thread = FlowStatisticThread()
            statistic_thread.start()

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
        if referrer.find(request.host) < 0:
            access.access_source = "external_access"
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

    def statistic_day(date):
        """根据指定"""
        pass


class FlowStatisticThread(threading.Thread):
    """流量统计线程类"""

    def run(self):
        date, delta = datetime.now(), timedelta(days=-1)
        date = date + delta
        FlowService.statistic_day(date)
