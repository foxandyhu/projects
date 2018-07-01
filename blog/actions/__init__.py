from flask import Blueprint, request

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


@adminBp.errorhandler(Exception)
def admin_error(e):
    message = json_utils.to_json(e.args[0])
    return message, 500


@adminBp.before_request
def request_intercept():
    """请求拦截器"""

    paths = ["/manage/login.html"]
    path = request.path
    if path not in paths:
        user = context_utils.get_current_user_session()
        if not user:
            return "未登录!", 401
