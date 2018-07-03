from actions import adminBp
from flask import render_template, request, redirect
from services import users_service, system_service
from utils import context_utils
from models.forms import UserLoginForm


@adminBp.route("/index.html", methods=["GET", "POST"])
def index():
    """后台首页"""

    return render_template("admin/base.html", login_user=context_utils.get_current_user_session())


@adminBp.route("/login.html", methods=["GET", "POST"])
def login():
    """后台登录"""

    if request.method == "GET":
        if context_utils.get_current_user_session():
            return redirect("/manage/index.html")
        return render_template("admin/login.html")

    form = UserLoginForm(request.form)
    if not form.validate():
        return render_template("admin/login.html", message="用户名或密码输入不正确!")
    username = form.username.data
    password = form.password.data
    captch = form.captch.data
    try:
        if captch.lower() != str(context_utils.get_from_session("captch")).lower():
            raise Exception("验证码不正确!")
        user = users_service.UserService.login(username, password)
        context_utils.set_current_user_session(user.username)
        return redirect("/manage/index.html")
    except Exception as e:
        return render_template("admin/login.html", message=e)


@adminBp.route("/logout.html")
def logout():
    """用户登出"""

    context_utils.del_current_user_session()
    return redirect("/manage/login.html")
