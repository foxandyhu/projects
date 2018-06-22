from admin import adminBp
from flask import render_template


@adminBp.route("/index.html")
def index():
    """后台首页"""
    return render_template("admin/index.html")
