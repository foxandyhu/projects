from extensions import db
from models import articles_model
from utils import context_utils, pagination_utils, file_utils


class ArticlesService(object):
    """文章业务接口"""

    @staticmethod
    def add_article(article, tag_ids):
        """添加文章"""

        if article.logo:
            source = context_utils.get_appdir() + article.logo  # LOGO源物理路径
            dest = context_utils.get_upload_article_dir()
            article.logo = context_utils.get_upload_article_dir_rlt() + file_utils.get_filename(source)

        tags = []
        if tag_ids and tag_ids.split(","):
            ids = tag_ids.split(",")
            for tid in ids:
                tag = ArticlesService.get_tag(tid)
                if tag:
                    tags.append(tag)
        article.tags = tags
        db.session.add(article)
        db.session.commit()

        if article.logo:
            file_utils.move_file(source, dest)

    @staticmethod
    def edit_article(article, tag_ids):
        """添加文章"""

        art = ArticlesService.get_article_id(article.id)
        has_new_logo = False
        if not art:
            raise Exception("文章不存在!")
        if article.logo and article.logo != art.logo:
            source = context_utils.get_appdir() + article.logo  # LOGO源物理路径
            dest = context_utils.get_upload_article_dir()
            article.logo = context_utils.get_upload_article_dir_rlt() + file_utils.get_filename(source)
            has_new_logo = True
        art.logo = article.logo

        tags = []
        if tag_ids and tag_ids.split(","):
            ids = tag_ids.split(",")
            for tid in ids:
                tag = ArticlesService.get_tag(tid)
                if tag:
                    tags.append(tag)
        art.tags = tags

        art.category_id = article.category_id
        art.title = article.title
        art.article = article.summary
        art.content = article.content
        art.seq = article.seq
        art.author = article.author
        art.source = article.source
        art.source_url = article.source_url
        art.is_top = article.is_top
        art.is_recommend = article.is_recommend
        art.is_verify = article.is_verify
        art.click_count = article.click_count
        art.publish_ip = article.publish_ip
        art.publish_time = article.publish_time
        art.member_id = article.member_id

        db.session.commit()

        if has_new_logo:
            file_utils.move_file(source, dest)

    @staticmethod
    def get_article_id(article_id):
        """根据文章ID获取文章"""

        result = articles_model.Article.query.filter(articles_model.Article.id == article_id).first()
        return result

    @staticmethod
    def get_page_article():
        """返回文章分页对象"""

        query = articles_model.Article.query.order_by(articles_model.Article.is_top.desc(),
                                                      articles_model.Article.seq.asc(),
                                                      articles_model.Article.id.desc())
        title = context_utils.get_from_g("title")
        if title:
            query = query.filter(articles_model.Article.title.like("%" + title + "%"))

        category_id = context_utils.get_from_g("cid")

        if category_id:
            category = ArticlesService.get_category_id(category_id)
            if category:
                if category.parent_id == 0:
                    sub_categorys = ArticlesService.get_category_pid(category.id)
                    if sub_categorys:
                        ids = [sub.id for sub in sub_categorys]
                        query = query.filter(articles_model.Article.category_id.in_(ids))
                    else:
                        query = query.filter(articles_model.Article.category_id == category_id)
                else:
                    query = query.filter(articles_model.Article.category_id == category_id)

        pagination = context_utils.get_pagination()
        pagination = query.paginate(pagination.page_no, pagination.page_size)
        if pagination.items:
            for item in pagination.items:
                if item.member:
                    item.username = item.member.username
                    del item.member
                if item.category:
                    item.category_name = item.category.name
                    del item.category
        pagination = pagination_utils.get_pagination_sqlalchemy(pagination)
        return pagination

    @staticmethod
    def edit_top_onoff(ids, flag):
        """设置文章置顶"""
        if not ids:
            raise Exception("未选择文章!")

        for aid in ids:
            article = ArticlesService.get_article_id(int(aid))
            if article:
                article.is_top = flag
                db.session.commit()

    @staticmethod
    def edit_recommend_onoff(ids, flag):
        """设置文章推荐"""
        if not ids:
            raise Exception("未选择文章!")

        for aid in ids:
            article = ArticlesService.get_article_id(int(aid))
            if article:
                article.is_recommend = flag
                db.session.commit()

    @staticmethod
    def edit_verify_onoff(ids, flag):
        """设置文章审核"""
        if not ids:
            raise Exception("未选择文章!")

        for aid in ids:
            article = ArticlesService.get_article_id(int(aid))
            if article:
                article.is_verify = flag
                db.session.commit()

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
