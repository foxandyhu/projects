from flask import render_template, request
from actions import adminBp
from services.flow_service import FlowReportService
from utils import json_utils, string_utils
from datetime import datetime, date
import psutil, time


@adminBp.route("/seo/pv.html")
def get_flow_pv():
    """获得统计PV，IP等数据"""

    type = request.args.get("type") if request.args.get("type") else 1
    if type == 1:  # 统计今天的流量
        results = FlowReportService.statistic_today()

    data = {"pvTotal": 0, "visitorTotal": 0, "ipTotal": 0, "list": results}

    for result in results:
        data["ipTotal"] = data["ipTotal"] + result[0]
        data["visitorTotal"] = data["visitorTotal"] + result[1]
        data["pvTotal"] = data["pvTotal"] + result[2]
    return json_utils.to_json(data)


@adminBp.route("/seo/source.html")
def get_flow_source():
    """根据来源统计"""

    now = datetime.now()
    begin = date(now.year, 1, 1)
    end = date(now.year, 12, 31)
    results = FlowReportService.statistic_source(begin, end)
    pv_total = 0
    data = dict()
    types = {"direct_access": "直接访问", "engine_access": "搜索引擎", "external_access": "外部链接"}
    for result in results:
        pv_total += result[2]
        if types[result[3]]:
            data[types[result[3]]] = result[2]
    result = dict()
    for key, value in data.items():
        result[f"{key} %.2f%%" % (value / pv_total * 100)] = value
    result = [[key, value] for key, value in result.items()]
    return json_utils.to_json(result)


@adminBp.route("/seo/keywords.html")
def get_flow_keyword():
    """根据关键字来源统计"""

    now = datetime.now()
    begin = date(now.year, 1, 1)
    end = date(now.year, 12, 31)
    results = FlowReportService.statistic_source(begin, end, "keyword", 10)
    pv_total = 0
    for result in results:
        pv_total += result[2]
    data = [[f"{result[3]}<br> %.2f%%" % (result[2] / pv_total * 100), result[2]] for result in results]
    return json_utils.to_json(data)


@adminBp.route("/seo/link.html")
def get_flow_link():
    """根据链接来源统计"""

    now = datetime.now()
    begin = date(now.year, 1, 1)
    end = date(now.year, 12, 31)
    results = FlowReportService.statistic_source(begin, end, "link", 10)
    pv_total = 0
    for result in results:
        pv_total += result[2]
    data = [[result[3], result[2], "%.2f%%" % (result[2] / pv_total * 100)] for result in results]
    return json_utils.to_json(data)


@adminBp.route("/seo/sys_res.html")
def get_sys_used():
    """获得系统的使用率"""

    cpu = psutil.cpu_percent()  # CPU使用率
    mem = psutil.virtual_memory().percent  # 内存使用率
    total_mem = string_utils.bytes2human(psutil.virtual_memory().total)
    used = string_utils.bytes2human(psutil.virtual_memory().used)
    timestamp = int(round(time.time() * 1000))

    data = [cpu, mem, used, total_mem, timestamp]
    return json_utils.to_json(data)


@adminBp.route("/seo/friendlink.html")
def frienlink_list():
    """友情链接"""

    return render_template("admin/seo/friendlink_list.html")
