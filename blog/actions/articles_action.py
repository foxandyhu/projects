from actions import adminBp
from flask import render_template, request
from services import articles_service
from utils import pagination_utils, json_utils, context_utils
from models import articles_model, forms, ResponseData
from datetime import datetime


@adminBp.route("/blog/articles.html")
def article_list():
    """文章列表"""

    pagination_utils.instantce_page()
    context_utils.put_to_g("title", 'title' in request.args and request.args.get("title"))
    context_utils.put_to_g("cid", 'cid' in request.args and request.args.get("cid"))
    pagination = articles_service.ArticlesService.get_page_article()
    return json_utils.to_json(pagination)


@adminBp.route("/blog/articles/settop.html")
def article_top_on():
    """设置文章置顶"""

    ids = "ids" in request.args and request.args.get("ids")
    ids = ids.split(",") if ids else None
    flag = "flag" in request.args and request.args.get("flag")
    flag = True if str(flag).lower() == "true" else False if flag else False
    articles_service.ArticlesService.edit_top_onoff(ids, flag)
    return json_utils.to_json(ResponseData.get_success())


@adminBp.route("/blog/articles/setrecommend.html")
def article_recommend_on():
    """设置文章是否推荐"""

    ids = "ids" in request.args and request.args.get("ids")
    ids = ids.split(",") if ids else None
    flag = "flag" in request.args and request.args.get("flag")
    flag = True if str(flag).lower() == "true" else False if flag else False
    articles_service.ArticlesService.edit_recommend_onoff(ids, flag)
    return json_utils.to_json(ResponseData.get_success())


@adminBp.route("/blog/articles/setverify.html")
def article_verify_on():
    """设置文章是否通过审核"""

    ids = "ids" in request.args and request.args.get("ids")
    ids = ids.split(",") if ids else None
    flag = "flag" in request.args and request.args.get("flag")
    flag = True if str(flag).lower() == "true" else False if flag else False
    articles_service.ArticlesService.edit_verify_onoff(ids, flag)
    return json_utils.to_json(ResponseData.get_success())


@adminBp.route("/blog/articles/add.html", methods=["POST"])
def add_articles():
    """添加文章"""

    form = forms.ArticlesForm(request.form)
    if not form.validate():
        raise Exception(form.errors)

    article = articles_model.Article()
    article.title = form.title.data
    article.content = form.content.data
    article.summary = form.summary.data
    article.category_id = request.form.get("category_id")
    article.seq = form.seq.data
    article.author = request.form.get("author")
    article.source = request.form.get("source")
    article.source_url = request.form.get("source_url")
    article.is_top = not request.form.get("is_top") is None
    article.is_recommend = not request.form.get("is_recommend") is None
    article.is_verify = not request.form.get("is_verify") is None
    article.click_count = 0
    article.publish_time = datetime.now()
    article.publish_ip = request.remote_addr
    tag_ids = request.form.get("tag_ids")

    articles_service.ArticlesService.add_article(article, tag_ids)
    return json_utils.to_json(ResponseData.get_success())


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

    result = articles_service.ArticlesService.get_category_pid(0)
    if result:
        for item in result:
            item.items = articles_service.ArticlesService.get_category_pid(item.id)
    return json_utils.to_json(result)


@adminBp.route("/blog/categorys/edit.html")
def edit_article_categorys():
    """修改文章类别"""

    form = forms.CategorysForm(request.args)
    if not form.validate():
        raise Exception(form.errors)
    category = articles_model.Category()
    category.name = form.name.data

    id = "id" in request.args and request.args.get("id")
    id = int(id) if id else 0
    category.id = id

    articles_service.ArticlesService.edit_category(category)
    return json_utils.to_json(ResponseData.get_success())


@adminBp.route("/blog/categorys/add.html")
def add_article_categorys():
    """添加文章类别"""

    form = forms.TagsForm(request.args)
    if not form.validate():
        raise Exception(form.errors)

    category = articles_model.Category()
    category.name = form.name.data

    pid = "parent_id" in request.args and request.args.get("parent_id")
    pid = int(pid) if pid else 0
    category.parent_id = pid
    articles_service.ArticlesService.add_category(category)
    return json_utils.to_json(ResponseData.get_success())


@adminBp.route("/blog/categorys/del-<int:category_id>.html")
def del_categorys(category_id):
    """删除文章类别"""

    articles_service.ArticlesService.del_category_id(category_id)
    return json_utils.to_json(ResponseData.get_success())


@adminBp.route("/blog/comments.html")
def article_comments():
    """评论管理"""

    return render_template("admin/articles/comment_list.html")
