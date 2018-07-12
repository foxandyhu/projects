from models.system_model import SysAccess, SysAccessPage
from flask import session
from utils import context_utils
from datetime import datetime
from extensions import cache
import itertools, re


class FlowService(object):
    """系统流量业务接口"""

    ACCESS_COUNT_SESSION_FLAG = "access_count_flag"  # 每个客户端访问的次数统计标识
    ACCESS_LAST_SESSION_TIME = "access_last_time"  # 每个客户端最后一次的访问时间
    ACCESS_CACHE_SITE_FLAG, SESSION_SITE_NAMESPACE = "_site_", "SESSION_SITE:"  # session访问站点记录 缓存标识
    ACCESS_CACHE_PAGE_FLAG, SESSION_PAGE_NAMESPACE = "_page_", "SESSION_PAGE:"  # 页面访问记录 缓存标识
    CACHE_FRESH_DB_TIME = datetime.now()  # 缓存中的数据写入数据库的时间标识（10分钟写入访问统计数据到数据库）

    @staticmethod
    def flow_statistic(request, page, referrer):
        ip = context_utils.get_client_request_ip(request)
        brower = context_utils.get_client_request_brower(request)
        os = context_utils.get_client_request_os(request)

        now, session_id = datetime.now(), session.sid

        access_count = session.get(FlowService.ACCESS_COUNT_SESSION_FLAG) or 0
        access_last_time = session.get(FlowService.ACCESS_LAST_SESSION_TIME)
        if access_count == 0:  # 新客户第一次访问
            access = FlowService.wrap_access(request, session_id, ip, page, referrer, brower, os, now)
            access.entry_page = page
            access_last_time = now
        else:
            access = FlowService.find_access(session_id)
            if access is None:  # 防止缓存和数据库中数据丢失
                access = FlowService.wrap_access(request, session_id, ip, page, referrer, brower, os, now)

        access_count += 1
        access.stop_page = page
        access.visit_page_count = access_count  # 更新访问次数和最后一次访问时间
        access.visit_second = (now - access_last_time).seconds

        access_page = FlowService.wrap_access_page(session_id, page, access_count, now)

        session[FlowService.ACCESS_COUNT_SESSION_FLAG] = access.visit_page_count
        session[FlowService.ACCESS_LAST_SESSION_TIME] = now

        cache.set(f"{FlowService.SESSION_SITE_NAMESPACE}{session_id}{FlowService.ACCESS_CACHE_SITE_FLAG}", access,
                  0)  # 把每个session的访问信息放入到缓存中，统一处理
        cache.set(f"{FlowService.SESSION_PAGE_NAMESPACE}{session_id}{FlowService.ACCESS_CACHE_PAGE_FLAG}{access_count}",
                  access_page, 0)

        if (now - FlowService.CACHE_FRESH_DB_TIME).seconds > 10 * 60:
            FlowService.flush_cache_to_db()

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

        access = cache.get(f"{FlowService.SESSION_SITE_NAMESPACE}{session_id}{FlowService.ACCESS_CACHE_SITE_FLAG}")
        if access is None:  # 如果缓存中不存在则从数据库中查找
            access = SysAccess.query.filter(SysAccess.session_id == session_id).first()
        return access

    @staticmethod
    def wrap_access_page(session_id, page, access_count, date_time):
        """包装访问页对象"""
        access_page = SysAccessPage()
        access_page.session_id = session_id
        access_page.p = page
        access_page.access_time = date_time.time()
        access_page.access_date = date_time.date()
        access_page.visit_second = 0
        access_page.seq = access_count

        pre_cache_key = f"{FlowService.SESSION_PAGE_NAMESPACE}" \
                        f"{session_id}{FlowService.ACCESS_CACHE_PAGE_FLAG}{access_count-1}"
        pre_page = cache.get(pre_cache_key)
        if pre_page is None:
            pre_page = SysAccessPage.query.filter(
                SysAccessPage.session_id == session_id and SysAccessPage.seq == access_count - 1).first()
        if pre_page:
            seconds = (date_time - datetime.combine(pre_page.access_date, pre_page.access_time)).seconds
            pre_page.visit_second = seconds
            cache.set(pre_cache_key, pre_page, 0)
        return access_page

    @staticmethod
    def flush_cache_to_db():
        """把缓存数据写入数据库"""
        pass
