from actions import adminBp
from flask import render_template


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
