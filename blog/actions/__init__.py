from flask import Blueprint, request, redirect

adminBp = Blueprint("adminBp", __name__)
templateAdminBp = Blueprint("templateAdminBp", __name__)

from actions import manage_action
from actions import system_action
from actions import members_action
from actions import articles_action
from actions import seo_action
from actions import users_action
from actions import logs_action
from actions import templates_action, files_action
from utils import json_utils, context_utils
from extensions import logger


@adminBp.errorhandler(Exception)
def admin_error(e):
    logger.error(e)
    if e.args and e.args[0]:
        message = json_utils.to_json(e.args[0])
        return message, (e.code if e.code else 500) if hasattr(e, "code") else 500
    return e.name, e.code


@adminBp.before_request
def request_intercept():
    """请求拦截器"""

    paths = ["/manage/login.html"]
    path = request.path
    if path not in paths:
        user = context_utils.get_current_user_session()
        xml_request = request.headers.get("X-Requested-With")
        if not user:
            if "XMLHttpRequest" == xml_request:
                return "未登录!", 401
            return redirect("/manage/login.html")
