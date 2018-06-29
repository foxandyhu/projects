from extensions import db
from models import Serializable

# 文章和标签中间表关系
article_tag_ship = db.Table("d_article_tag_ship",
                            db.Column("article_id", db.Integer, db.ForeignKey("d_articles.id"), primary_key=True),
                            db.Column("d_tags", db.Integer, db.ForeignKey("d_tags.id"), primary_key=True))


class Article(Serializable, db.Model):
    """博客文章"""
    __tablename__ = "d_articles"

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    title = db.Column(db.String(50), nullable=False)  # 文章标题
    summary = db.Column(db.String(200))  # 文章概要
    content = db.Column(db.Text, nullable=False)  # 文章内容
    click_count = db.Column(db.Integer, default=0)  # 文章点击率
    is_recommend = db.Column(db.Boolean, default=False)  # 是否推荐
    is_top = db.Column(db.Boolean, default=False)  # 是否置顶
    is_verify = db.Column(db.Boolean, default=False)  # 审核是否通过
    seq = db.Column(db.Integer, default=0)  # 排序
    logo = db.Column(db.String(200))  # 文章Logo图片
    author = db.Column(db.String(50))  # 作者
    source = db.Column(db.String(50))  # 来源
    source_url = db.Column(db.String(200))  # 来源地址
    publish_time = db.Column(db.DateTime, nullable=False)  # 发布时间
    publish_ip = db.Column(db.String(30))  # 发布IP

    member_id = db.Column(db.Integer, db.ForeignKey("d_members.id"))  # 发布者ID
    member = db.relationship("members_model.Member", lazy="joined")

    category_id = db.Column(db.Integer, db.ForeignKey("d_categorys.id"))  # 文章类别
    category = db.relationship("Category", lazy="joined")

    tags = db.relationship("Tag", secondary=article_tag_ship, backref="articles")


class Category(Serializable, db.Model):
    """文章分类"""
    __tablename__ = "d_categorys"

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    name = db.Column(db.String(15), nullable=False)  # 类别名称
    seq = db.Column(db.Integer, default=0)  # 排序序号
    parent_id = db.Column(db.Integer)  # 父类别ID


class Tag(Serializable, db.Model):
    """文章标签"""
    __tablename__ = "d_tags"

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    name = db.Column(db.String(20), nullable=False, unique=True)  # 标签名称


class Comment(Serializable, db.Model):
    """文章评论"""
    __tablename__ = "d_comments"

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    content = db.Column(db.Text, nullable=False)  # 评论内容
    publish_time = db.Column(db.DateTime, nullable=False)  # 发布时间
    publish_ip = db.Column(db.String(30))  # 发布IP

    member_id = db.Column(db.Integer)  # 发布者ID

    article_id = db.Column(db.Integer)  # 文章ID

    parent_id = db.Column(db.Integer)  # 引用评论父ID

    is_show = db.Column(db.Boolean, default=False)  # 评论是否显示
