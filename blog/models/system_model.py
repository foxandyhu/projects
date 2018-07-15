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


class SysBanner(Serializable, db.Model):
    """系统Banner"""

    __tablename__ = "sys_banner"
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    title = db.Column(db.String(10), nullable=False)  # 名称
    link = db.Column(db.String(200))  # 链接地址
    action = db.Column(db.String(10))  # 打开方式
    seq = db.Column(db.Integer, default=0)  # 排序序号
    logo = db.Column(db.String(200), nullable=False)  # 图片地址


class SysAccess(Serializable, db.Model):
    """系统访问统计"""

    __tablename__ = "sys_access"
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    session_id = db.Column(db.String(64), nullable=False)  # session_id
    access_time = db.Column(db.Time, nullable=False)  # 访问时间
    access_date = db.Column(db.Date, nullable=False)  # 访问日期
    access_ip = db.Column(db.String(50), nullable=False)  # 访问IP
    access_area = db.Column(db.String(50))  # 访问区域
    access_source = db.Column(db.String(250))  # 访问来源
    external_link = db.Column(db.String(255))  # 外部连接地址
    engine = db.Column(db.String(20))  # 访问搜索引擎
    entry_page = db.Column(db.String(255))  # 入口页面
    stop_page = db.Column(db.String(255))  # 最后停留页面
    visit_second = db.Column(db.Integer, default=0)  # 访问时长/秒
    visit_page_count = db.Column(db.Integer, default=0)  # 访问页面数
    operating_system = db.Column(db.String(50))  # 操作系统
    browser = db.Column(db.String(50))  # 浏览器
    keyword = db.Column(db.String(255))  # 来访关键字


class SysAccessPage(Serializable, db.Model):
    """每个页面的访问详情"""

    __tablename__ = "sys_access_pages"
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    session_id = db.Column(db.String(64), nullable=False)  # session_id
    access_page = db.Column(db.String(255), nullable=False)  # 访问的页面
    access_time = db.Column(db.Time, nullable=False)  # 访问时间
    access_date = db.Column(db.Date, nullable=False)  # 访问日期
    visit_second = db.Column(db.Integer, nullable=False)  # 访问时长/秒
    seq = db.Column(db.Integer, default=0, nullable=False)  # 页面访问顺序


class SysAccessStatistic(Serializable, db.Model):
    """统计流量数据"""

    __tablename__ = "sys_access_statistic"
    id = db.Column(db.Integer, autoincrement=True, primary_key=True)
    statistic_date = db.Column(db.Date, nullable=False)  # 统计日期
    pv = db.Column(db.Integer, default=0, nullable=False)  # PV
    ip = db.Column(db.Integer, default=0, nullable=False)  # IP
    visitors = db.Column(db.Integer, default=0, nullable=False)  # 访客数
    pages_aver = db.Column(db.Integer, default=0, nullable=False)  # 人均浏览次数
    visit_second_aver = db.Column(db.Integer, default=0, nullable=False)  # 人均访问时长/秒
    statistic_key = db.Column(db.String(255), nullable=False)  # 统计分类
    statistic_value = db.Column(db.String(255))  # 统计分类值


class SysAccessCount(Serializable, db.Model):
    """系统每日的总访问量数据"""

    __tablename__ = "sys_access_count"
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    page_count = db.Column(db.Integer, default=0, nullable=False)  # 访问页数
    visitors = db.Column(db.Integer, default=0, nullable=False)  # 访问用户数
    statistic_date = db.Column(db.Date, nullable=False)  # 统计日期


class SysAccessCountHour(Serializable, db.Model):
    """系统每小时的访问量数据"""

    __tablename__ = "sys_access_count_hour"
    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    hour_pv = db.Column(db.Integer, default=0, nullable=False)  # 小时PV
    hour_ip = db.Column(db.Integer, default=0, nullable=False)  # 小时Ip
    hour_uv = db.Column(db.Integer, default=0, nullable=False)  # 小时访客数
    statistic_date = db.Column(db.Date, nullable=False)  # 统计日期
    statistic_time = db.Column(db.Integer, nullable=False)  # 统计小时
