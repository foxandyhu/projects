from actions import adminBp
from flask import render_template,request
from services import articles_service
from utils import pagination_utils,json_utils,context_utils


@adminBp.route("/blog/articles.html")
def article_list():
    """文章列表"""

    return render_template("admin/articles/article_list.html")


@adminBp.route("/blog/tags.html")
def article_tags():
    """标签列表"""

    pagination_utils.instantce_page()
    context_utils.put_to_g("name", 'name' in request.args and request.args.get("name"))
    pagination = articles_service.ArticlesService.get_page_tag()
    return json_utils.to_json(pagination)


@adminBp.route("/blog/categorys.html")
def article_categorys():
    """类别管理"""

    return render_template("admin/articles/category_list.html")


@adminBp.route("/blog/comments.html")
def article_comments():
    """评论管理"""

    return render_template("admin/articles/comment_list.html")
