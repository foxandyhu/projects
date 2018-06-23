from flask import render_template
from actions import adminBp


@adminBp.route("/system/web_setting.html")
def web_setting():
    """网站设置"""

    return render_template("admin/system/web_setting.html")


@adminBp.route("/system/web_template.html")
def web_template():
    """网站模板设置"""

    return render_template("admin/system/web_template.html")


@adminBp.route("/system/tasks.html")
def system_tasks():
    """系统任务"""

    return render_template("admin/system/tasks.html")


@adminBp.route("/system/about.html")
def system_about():
    """关于我们"""

    return render_template("admin/system/about.html")


@adminBp.route("/system/contact.html")
def system_contact():
    """联系我们"""

    return render_template("admin/system/contact.html")


@adminBp.route("/system/editpwd.html")
def editpwd():
    """修改密码"""

    return render_template("admin/system/editpwd.html")