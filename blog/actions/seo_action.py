from flask import request
from actions import adminBp
from services.flow_service import FlowReportService
from services.seo_service import FriendLinkService
from utils import json_utils, string_utils, pagination_utils, date_utils
from datetime import datetime, date, timedelta
from models import ResponseData
from models.forms import FriendlinkForm
from models.system_model import FriendLink
import psutil, time


@adminBp.route("/seo/pv.html")
def get_flow_pv():
    """获得统计PV，IP等数据"""

    type_id = int(request.args.get("type") if request.args.get("type") else 1)
    if type_id == 1:  # 统计今天的流量
        begin = datetime.now().date()
        end = begin
        period = date_utils.parse_date_str(datetime.now())
    elif type_id == 2:  # 统计本周
        day_of_week = datetime.now().isoweekday()
        delta = timedelta(days=1 - day_of_week)
        begin = datetime.now().date() + delta
        end = datetime.now().date()
        period = f"{date_utils.parse_date_str(begin)} 至 {date_utils.parse_date_str(end)}"
    elif type_id == 3:  # 统计本月
        begin = datetime.now().replace(datetime.now().year, datetime.now().month, 1).date()
        end = datetime.now().date()
        period = f"{datetime.now().year}-{datetime.now().month}"
    elif type_id == 4:  # 统计本年
        begin = datetime.now().replace(datetime.now().year, 1, 1).date()
        end = datetime.now().date()
        period = f"{datetime.now().year}"
    elif type_id == 5:  # 指定时间段
        begin = date_utils.parse_str_date(request.args.get("begin"))
        end = date_utils.parse_str_date(request.args.get("end"))
        period = f"{begin} 至 {end}"

    data = {"pvTotal": 0, "visitorTotal": 0, "ipTotal": 0, "list": {}, "period": period}

    if type_id == 1:
        days = [f"0{i}:00-0{i}:59" if i < 10 else f"{i}:00-{i}:59" for i in range(24)]
        results = FlowReportService.statistic_flow_today()
    else:
        count = (end - begin).days + 1
        if type_id == 4:  # 按年统计 以月份的形式展示
            now = datetime.now()
            days = [f"{now.year}-0{month}" if month < 10 else f"{now.year}-{month}" for month in
                    range(1, now.month + 1)]
        else:
            days = [date_utils.parse_date_str(end + timedelta(days=i + 1)) for i in range(-count, 0)]
        results = FlowReportService.statistic_source_date(begin, end, key_word="all")

    detail = dict()
    for day in days:  # 线形图根据类型和时间统计
        detail[day] = [0, 0, 0]
        for result in results:
            if type_id == 1:
                date_str = result[3]
            else:
                date_str = result[4]
            if type_id == 4:
                d = date_utils.parse_str_date(date_str)
                date_str = f"{d.year}-0{d.month}" if d.month < 10 else f"{d.year}-{d.month}"
            if detail.get(day) and date_str == day:
                ip = detail[day][0]
                uv = detail[day][1]
                pv = detail[day][2]
                detail[day] = [ip + result[0], uv + result[1], pv + result[2]]
    data["list"] = detail
    for result in results:
        data["ipTotal"] = data["ipTotal"] + result[0]
        data["visitorTotal"] = data["visitorTotal"] + result[1]
        data["pvTotal"] = data["pvTotal"] + result[2]
    return json_utils.to_json(data)


def get_source_key(category, key):
    types = {"direct_access": "直接访问", "engine_access": "搜索引擎", "external_access": "外部链接"}
    if category == "source":
        return types[key]
    return key


@adminBp.route("/seo/source.html")
def get_flow_source():
    """根据来源统计"""

    type_id = int(request.args.get("type") if request.args.get("type") else 4)
    category = request.args.get("category") or "source"
    c_names = {"source": "来源分类", "engine": "搜索引擎", "link": "来访域名", "keyword": "搜索词"}
    c_name = c_names[category]
    if type_id == 2:  # 统计本周
        day_of_week = datetime.now().isoweekday()
        delta = timedelta(days=1 - day_of_week)
        begin = datetime.now().date() + delta
        end = datetime.now().date()
        period = f"{date_utils.parse_date_str(begin)} 至 {date_utils.parse_date_str(end)}"
    elif type_id == 3:  # 统计本月
        begin = datetime.now().replace(datetime.now().year, datetime.now().month, 1).date()
        end = datetime.now().date()
        period = f"{datetime.now().year}-{datetime.now().month}"
    elif type_id == 4:  # 统计本年
        begin = datetime.now().replace(datetime.now().year, 1, 1).date()
        end = datetime.now().date()
        period = f"{datetime.now().year}"
    elif type_id == 5:  # 指定时间段
        begin = date_utils.parse_str_date(request.args.get("begin"))
        end = date_utils.parse_str_date(request.args.get("end"))
        period = f"{begin} 至 {end}"

    data = {"pv_total": 0, "ip_total": 0, "uv_total": 0, "items": {}, "list": {}, "period": period, "c_name": c_name}

    results = FlowReportService.statistic_source_type(begin, end, key_word=category)
    pv_total, ip_total, uv_total = 0, 0, 0
    source_map = dict()
    for result in results:  # 饼形图统根据类型统计
        pv_total += result[2]
        ip_total += result[0]
        uv_total += result[1]
        key = get_source_key(category, result[3])
        source_item = [result[0], result[1], result[2]]
        if key:
            if source_map.get(key):
                source_item[0] += source_map[key][0]
                source_item[1] += source_map[key][1]
                source_item[2] += source_map[key][2]
            source_map[key] = source_item

    detail = dict()
    count = (end - begin).days + 1
    if type_id == 4:  # 按年统计 以月份的形式展示
        now = datetime.now()
        days = [f"{now.year}-0{month}" if month < 10 else f"{now.year}-{month}" for month in range(1, now.month + 1)]
    else:
        days = [date_utils.parse_date_str(end + timedelta(days=i + 1)) for i in range(-count, 0)]
    results = FlowReportService.statistic_source_date(begin, end, key_word=category)
    category_values = {get_source_key(category, result[3]): get_source_key(category, result[3]) for result in
                       results}  # 报表的类别数需要以至防止画图数据乱
    for day in days:  # 线形图根据类型和时间统计
        detail[day] = {cv: [0, 0, 0] for cv in category_values}
        for result in results:
            key = get_source_key(category, result[3])
            date_str = result[4]
            if type_id == 4:
                d = date_utils.parse_str_date(date_str)
                date_str = f"{d.year}-0{d.month}" if d.month < 10 else f"{d.year}-{d.month}"
            if date_str == day:
                if detail[date_str].get(key):
                    ip = detail[date_str][key][0]
                    uv = detail[date_str][key][1]
                    pv = detail[date_str][key][2]
                else:
                    ip, uv, pv = 0, 0, 0
                detail[date_str][key] = [ip + result[0], uv + result[1], pv + result[2]]

    data["pv_total"] = pv_total
    data["ip_total"] = pv_total
    data["uv_total"] = pv_total
    data["items"] = source_map
    data["list"] = detail

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

    pagination_utils.instantce_page(10)
    pagination = FriendLinkService.get_friendlinks()
    return json_utils.to_json(pagination)


@adminBp.route("/seo/friendlink/add.html", methods=["POST"])
def add_friendlink():
    """添加友情链接"""

    form = FriendlinkForm(request.form)
    if not form.validate():
        raise Exception([value for key, value in form.errors.items()][0][0])

    friendlink = FriendLink()
    friendlink.name = form.name.data
    friendlink.link = form.link.data
    friendlink.seq = form.seq.data

    FriendLinkService.add_friendlink(friendlink)
    return json_utils.to_json(ResponseData.get_success())


@adminBp.route("/seo/friendlink/edit.html", methods=["POST"])
def edit_friendlink():
    """编辑友情链接"""

    form = FriendlinkForm(request.form)
    if not form.validate():
        raise Exception(form.errors)

    friendlink = FriendLink()
    friendlink.name = form.name.data
    friendlink.link = form.link.data
    friendlink.seq = form.seq.data
    friendlink.id = request.form.get("id")

    FriendLinkService.edit_friendlink(friendlink)
    return json_utils.to_json(ResponseData.get_success())


@adminBp.route("/seo/friendlink/del/<int:friendlink_id>.html")
def del_friendlink(friendlink_id):
    """删除友情链接"""

    FriendLinkService.del_friendlink(friendlink_id)
    return json_utils.to_json(ResponseData.get_success())
