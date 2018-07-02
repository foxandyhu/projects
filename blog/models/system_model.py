from extensions import db


class SysSetting(db.Model):
    """系统管理用户"""
    __tablename__ = "sys_setting"

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    name = db.Column(db.String(50), nullable=False)  # 站点名称
    website = db.Column(db.String(128), nullable=False)  # 域名
    key_words = db.Column(db.String(200))  # 站点关键字
    remark = db.Column(db.String(200))  # 站点描述
    is_enable = db.Column(db.Boolean, nullable=False, default=False)  # 是否启用站点
    logo = db.Column(db.String(200), nullable=False)  # 网站LOGO
