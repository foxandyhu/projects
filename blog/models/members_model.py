from extensions import db
from models import Serializable


class Member(Serializable, db.Model):
    """博客用户类"""
    __tablename__ = "d_members"

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    username = db.Column(db.String(50), nullable=False, unique=True)  # 用户名--邮件或手机
    password = db.Column(db.String(128), nullable=False)  # 密码
    salt = db.Column(db.String(12), nullable=False)  # 密码盐
    is_enable = db.Column(db.Boolean, nullable=False, default=False)  # 是否启用
    face = db.Column(db.String(200), nullable=False)  # 头像
    is_verify = db.Column(db.Boolean, nullable=False, default=False)  # 是否审核通过
    nickname = db.Column(db.String(30), nullable=False)  # 昵称
    reg_time = db.Column(db.DateTime, nullable=False)  # 注册时间
    reg_ip = db.Column(db.String(30))
