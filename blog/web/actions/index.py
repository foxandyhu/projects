from web import webBp
from flask import render_template


@webBp.route("/")
@webBp.route("/index.html")
def index():
    """博客首页"""

    return render_template("index.html")


@webBp.route("/detail.html")
def article_detail():
    """文章详情"""

    return render_template("info.html")


@webBp.route("/list.html")
def article_list():
    """文章列表"""

    return render_template("list.html")


@webBp.route("/lvmsg.html")
def leave_message():
    """博客留言"""

    return render_template("gbook.html")


@webBp.route("/about.html")
def about_blog():
    """关于博客"""

    return render_template("about.html")
