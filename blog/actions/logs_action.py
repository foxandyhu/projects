from actions import adminBp
from flask import render_template


@adminBp.route("/logs/login.html")
def loginlog_list():
    """登录日志"""

    return render_template("admin/logs/loginlog_list.html")


@adminBp.route("/logs/op.html")
def oplog_list():
    """操纵日志"""

    return render_template("admin/logs/oplog_list.html")


@adminBp.route("/logs/logfile.html")
def logfile_list():
    """服务器日志文件"""

    return render_template("admin/logs/logfile_list.html")