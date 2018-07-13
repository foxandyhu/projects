from web import webBp
from flask import request
from services.flow_service import FlowService
from extensions import logger


@webBp.route("/flow_statistic.html")
def flow_statistic():
    """网站流量统计"""

    referrer = request.args.get("referrer")
    page = request.args.get("page")

    try:
        FlowService.flow_statistic(request, page, referrer)
    except Exception as e:
        logger.error(e)
    return ""
