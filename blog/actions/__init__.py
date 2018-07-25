from flask import Blueprint, request, redirect, current_app

adminBp = Blueprint("adminBp", __name__)
templateAdminBp = Blueprint("templateAdminBp", __name__)


def NoNeedlogin(fun):
    """该函数作为目标函数的装饰器用来标识目标函数是否需要用户登录"""

    def no_need_login(*args, **kwargs):
        return fun()

    return no_need_login


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

    fun = current_app.view_functions[request.url_rule.endpoint]
    if fun.__name__ != NoNeedlogin(None).__name__:
        user = context_utils.get_current_user_session()
        xml_request = request.headers.get("X-Requested-With")
        if not user:
            if "XMLHttpRequest" == xml_request:
                return "未登录!", 401
            return redirect("/manage/login.html")
