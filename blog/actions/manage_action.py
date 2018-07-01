from actions import adminBp
from flask import render_template, request, redirect, flash, url_for
from services import users_service
from utils import context_utils
from models.forms import UserLoginForm


@adminBp.route("/index.html")
def index():
    """后台首页"""
    return render_template("admin/base.html")


@adminBp.route("/login.html", methods=["GET", "POST"])
def login():
    """后台登录"""

    if request.method == "GET":
        return render_template("admin/login.html")

    form = UserLoginForm(request.form)
    if not form.validate():
        return render_template("admin/login.html", message="用户名或密码输入不正确!")
    username = request.form.get("username")
    password = request.form.get("password")
    captch = request.form.get("captch")

    try:
        user = users_service.UserService.login(username, password)
        context_utils.set_current_user_session(user)
        return redirect("/manage/index.html")
    except Exception as e:
        return render_template("admin/login.html", message=e)
