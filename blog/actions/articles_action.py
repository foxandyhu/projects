from actions import adminBp
from flask import render_template, request
from services import articles_service
from utils import pagination_utils, json_utils, context_utils
from models import articles_model, forms, ResponseData


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


@adminBp.route("/blog/tags/add.html")
def add_article_tags():
    """添加文章标签"""

    form = forms.TagsForm(request.args)
    if not form.validate():
        raise Exception(form.errors)

    tag = articles_model.Tag()
    tag.name = form.name.data
    articles_service.ArticlesService.add_tag(tag)
    return json_utils.to_json(ResponseData.get_success())


@adminBp.route("/blog/tags/edit.html")
def edit_article_tags():
    """修改文章标签"""

    form = forms.TagsForm(request.args)
    if not form.validate():
        raise Exception(form.errors)
    tag = articles_model.Tag()
    tag.name = form.name.data

    id = "id" in request.args and request.args.get("id")
    id = int(id) if id else 0
    tag.id = id

    articles_service.ArticlesService.edit_tag(tag)
    return json_utils.to_json(ResponseData.get_success())


@adminBp.route("/blog/categorys.html")
def article_categorys():
    """类别管理"""

    return render_template("admin/articles/category_list.html")


@adminBp.route("/blog/comments.html")
def article_comments():
    """评论管理"""

    return render_template("admin/articles/comment_list.html")
