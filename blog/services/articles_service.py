from extensions import db
from models import articles_model
from utils import context_utils, pagination_utils


class ArticlesService(object):
    """文章业务接口"""

    @staticmethod
    def add_article(article):
        """添加文章"""

        db.session.add(article)
        db.session.commit()

    @staticmethod
    def get_article(article_id):
        """根据文章ID获取文章"""

        result = articles_model.Article.query.filter(articles_model.Article.id == article_id).first()
        return result

    @staticmethod
    def get_page_article():
        """返回文章分页对象"""

        pagination = context_utils.get_pagination()
        title = context_utils.get_from_g("title")
        query = articles_model.Article.query
        if title:
            query = query.filter(articles_model.Article.title.like("%" + title + "%"))
        pagination = query.paginate(pagination.page_no, pagination.page_size)
        pagination = pagination_utils.get_pagination_sqlalchemy(pagination)
        return pagination

    @staticmethod
    def add_tag(tag):
        """添加文章标签"""

        db.session.add(tag)
        db.session.commit()

    @staticmethod
    def get_tag(tag_id):
        """根据文章标签ID获取标签"""

        result = articles_model.Article.query.filter(articles_model.Tag.id == tag_id).first()
        return result

    @staticmethod
    def get_page_tag():
        """返回文章标签分页对象"""

        pagination = context_utils.get_pagination()
        name = context_utils.get_from_g("name")
        query = articles_model.Tag.query
        if name:
            query = query.filter(articles_model.Tag.name.like("%" + name + "%"))
        pagination = query.paginate(pagination.page_no, pagination.page_size)
        pagination = pagination_utils.get_pagination_sqlalchemy(pagination)
        return pagination
