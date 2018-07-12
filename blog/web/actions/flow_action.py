from web import webBp
from flask import request
from services.flow_service import FlowService


@webBp.route("/flow_statistic.html")
def flow_statistic():
    """网站流量统计"""

    referrer = request.args.get("referrer")
    page = request.args.get("page")

    try:
        FlowService.flow_statistic(request, page, referrer)
    except Exception as e:
        print(e)
        pass
    return ""
