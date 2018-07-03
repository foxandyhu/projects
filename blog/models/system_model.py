from extensions import db
from models import Serializable


class SysInfo(Serializable, db.Model):
    """系统管理用户"""
    __tablename__ = "sys_info"

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    name = db.Column(db.String(50), nullable=False)  # 站点名称
    website = db.Column(db.String(100), nullable=False)  # 域名
    key_words = db.Column(db.String(200))  # 站点关键字
    remark = db.Column(db.String(200))  # 站点描述
    is_enable = db.Column(db.Boolean, default=False)  # 是否启用站点
    logo = db.Column(db.String(200))  # 网站LOGO


class SysCopyRight(Serializable, db.Model):
    """系统版权信息"""

    __tablename__ = "sys_copyright"

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    icp = db.Column(db.String(30))  # ICP备案号
    copyright = db.Column(db.String(50))  # 版权
    organizer = db.Column(db.String(50))  # 主办单位
    contacts = db.Column(db.String(20))  # 联系人
    phone = db.Column(db.String(30))  # 联系电话
    address = db.Column(db.String(50))  # 联系地址


class SysNavigatorBar(Serializable, db.Model):
    """系统导航栏"""

    __tablename__ = "sys_nav_bar"
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    name = db.Column(db.String(10), nullable=False)  # 名称
    link = db.Column(db.String(100))  # 链接地址
    action = db.Column(db.String(10))  # 打开方式
    seq = db.Column(db.Integer, default=0)  # 排序序号
