from web import webBp
from flask import render_template, redirect
from services.articles_service import ArticlesService
from utils import pagination_utils, context_utils


@webBp.route("/")
@webBp.route("/index.html")
def index():
    """博客首页"""

    top_articles = ArticlesService.get_top_article(4)
    recommend_articles = ArticlesService.get_recommend_article(20)
    click_count_articles = ArticlesService.get_clickcount_article(6)
    recommend_articles2 = []
    if len(recommend_articles) > 14:
        recommend_articles2 = recommend_articles[14:]
        recommend_articles = recommend_articles[0:14]

    return render_template("index.html", topArticles=top_articles, recommendArticles=recommend_articles,
                           recommendArticles2=recommend_articles2, clickCountArticles=click_count_articles)


@webBp.route("/article/<int:article_id>.html")
def article_detail(article_id):
    """文章详情"""

    article = ArticlesService.get_article_id(article_id)
    if article and article.is_verify:
        rlt_article = ArticlesService.get_rlt_category_article(article.id, article.category_id, 6)
        next_article = ArticlesService.get_next_article(article.id, article.category_id)
        prev_article = ArticlesService.get_prev_article(article.id, article.category_id)
        ArticlesService.edit_click_count_article(article.id)
        return render_template("detail.html", article=article, rltArticle=rlt_article, nextArticle=next_article,
                               prevArticle=prev_article)
    return redirect("/index.html")


@webBp.route("/article/category/<int:category_id>.html")
def article_list(category_id):
    """文章列表"""

    pagination_utils.instantce_page(10)
    context_utils.put_to_g("cid", category_id)
    pager = ArticlesService.get_page_article(True)
    category = ArticlesService.get_category_id(category_id)
    categorys = ArticlesService.get_category_pid(category.parent_id)
    click_count_articles = ArticlesService.get_clickcount_article(6, category_id)
    recommend_article = ArticlesService.get_recommend_article(6, category_id)

    current_page = int((pager.page_no - 1) / 5) + 1
    end = current_page * 5
    end = pager.page_count if end > pager.page_count else end
    end = end + 1

    begin = (current_page - 1) * 5 + 1
    pp = range(begin, end)
    return render_template("list.html", pager=pager, category=category, categorys=categorys,
                           clickCountArticles=click_count_articles,
                           recommendArticle=recommend_article, pp=pp)


@webBp.route("/lvmsg.html")
def leave_message():
    """博客留言"""

    return render_template("gbook.html")


@webBp.route("/about.html")
def about_blog():
    """关于博客"""

    return render_template("about.html")
