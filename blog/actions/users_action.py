from actions import adminBp
from flask import render_template, request
from services import users_service
from utils import context_utils, json_utils
from models import ResponseData


@adminBp.route("/organize/users.html")
def user_list():
    """用户列表"""

    return render_template("admin/users/user_list.html")


@adminBp.route("/organize/roles.html")
def role_list():
    """角色列表"""

    return render_template("admin/users/role_list.html")


@adminBp.route("/organize/menus.html")
def menu_list():
    """菜单列表"""

    return render_template("admin/users/menu_list.html")


@adminBp.route("/organize/users/pwd.html", methods=["POST"])
def edit_pwd():
    """修改密码"""

    password = request.form.get("password")
    username = context_utils.get_current_user_session()
    user = users_service.UserService.get_user_username(username)
    users_service.UserService.edit_user_pwd(user.id, password)
    return json_utils.to_json(ResponseData.get_success())
