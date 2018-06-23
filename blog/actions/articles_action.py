from actions import adminBp
from flask import render_template


@adminBp.route("/blog/articles.html")
def article_list():
    """文章列表"""

    return render_template("admin/articles/article_list.html")


@adminBp.route("/blog/tags.html")
def article_tags():
    """标签列表"""

    return render_template("admin/articles/tags_list.html")


@adminBp.route("/blog/categorys.html")
def article_categorys():
    """类别管理"""

    return render_template("admin/articles/category_list.html")


@adminBp.route("/blog/comments.html")
def article_comments():
    """评论管理"""

    return render_template("admin/articles/comment_list.html")
