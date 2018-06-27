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
        query = articles_model.Article.query.order_by(articles_model.Article.id.desc())
        if title:
            query = query.filter(articles_model.Article.title.like("%" + title + "%"))
        pagination = query.paginate(pagination.page_no, pagination.page_size)
        pagination = pagination_utils.get_pagination_sqlalchemy(pagination)
        return pagination

    @staticmethod
    def add_tag(tag):
        """添加文章标签"""

        tag.name = tag.name.strip()
        t = ArticlesService.get_tag_name(tag.name)
        if t:
            raise Exception("标签名称已存在!")
        db.session.add(tag)
        db.session.commit()

    @staticmethod
    def edit_tag(tag):
        """修改文章标签"""

        t = ArticlesService.get_tag(tag.id)
        if not t:
            raise Exception("标签不存在!")

        tag.name = tag.name.strip()
        if tag.name == t.name:
            return
        if tag.name.lower() != t.name.lower():
            tt = ArticlesService.get_tag_name(tag.name)
            if tt:
                raise Exception("该标签已存在!")
        t.name = tag.name
        db.session.commit()

    @staticmethod
    def get_tag(tag_id):
        """根据文章标签ID获取标签"""

        result = articles_model.Tag.query.filter(articles_model.Tag.id == tag_id).first()
        return result

    @staticmethod
    def get_tag_name(name):
        """根据文章标签名称获取标签"""
        result = articles_model.Tag.query.filter(articles_model.Tag.name == name).first()
        return result

    @staticmethod
    def get_page_tag():
        """返回文章标签分页对象"""

        pagination = context_utils.get_pagination()
        name = context_utils.get_from_g("name")
        query = articles_model.Tag.query.order_by(articles_model.Tag.id
                                                  .desc())
        if name:
            query = query.filter(articles_model.Tag.name.like("%" + name + "%"))
        pagination = query.paginate(pagination.page_no, pagination.page_size)
        pagination = pagination_utils.get_pagination_sqlalchemy(pagination)
        return pagination

    @staticmethod
    def get_category_pid(category_pid):
        """根据父ID查找类别"""

        result = articles_model.Category.query.filter(articles_model.Category.parent_id == category_pid).order_by(
            articles_model.Category.seq.asc()).all()
        return result

    @staticmethod
    def get_category_id(category_id):
        """根据ID查找类别别"""

        result = articles_model.Category.query.filter(articles_model.Category.id == category_id).first()
        return result

    @staticmethod
    def get_category_name(name):
        """根据文章类别名称获取类别"""
        result = articles_model.Tag.query.filter(articles_model.Category.name == name).first()
        return result

    @staticmethod
    def edit_category(category):
        """修改文章类别"""

        c = ArticlesService.get_category_id(category.id)
        if not c:
            raise Exception("类别名称不存在!")

        category.name = category.name.strip()
        if category.name == c.name:
            return
        if category.name.lower() != c.name.lower():
            tt = ArticlesService.get_tag_name(category.name)
            if tt:
                raise Exception("该类别名称已存在!")
        c.name = category.name
        db.session.commit()

    @staticmethod
    def add_category(category):
        """添加文章类别"""

        category.name = category.name.strip()
        t = ArticlesService.get_category_name(category.name)
        if t:
            raise Exception("类别名称已存在!")

        list = ArticlesService.get_category_pid(category.parent_id)
        seq = len(list)
        category.seq = seq + 1
        db.session.add(category)
        db.session.commit()

    @staticmethod
    def del_category_id(category_id):
        """添加文章类别"""

        category = ArticlesService.get_category_id(category_id)
        if not category:
            return
        categorys = ArticlesService.get_category_pid(category.id)
        if categorys:
            for c in categorys:
                ArticlesService.del_category_id(c.id)
        db.session.delete(category)
        db.session.commit()
