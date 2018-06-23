from extensions import db


class User(db.Model):
    """系统管理用户"""
    __tablename__ = "d_users"

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    username = db.Column(db.String(50), nullable=False, unique=True)
    password = db.Column(db.String(128), nullable=False)
    is_enable = db.Column(db.Boolean, nullable=False, default=False)
