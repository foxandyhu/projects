from extensions import db


class LogType:
    """日志类型"""
    LOGIN = 100  # 登录
    LOGOUT = 200  # 登出


class UserType:
    USER = 1000  # 普通博客用户
    ADMIN = 2000  # 系统管理员


class LoginLogs(db.Model):
    """用户登录登出相关日志"""
    __tablename__ = "sys_login_logs"

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    log_type = db.Column(db.Integer, default=0, nullable=False)
    user_type = db.Column(db.Integer, default=0, nullable=False)  # 用户类型
    username = db.Column(db.String(50), nullable=False)  # 用户名
    login_time = db.Column(db.DateTime, nullable=False)  # 登录时间
    login_ip = db.Column(db.String(30))  # 登录IP
    login_area = db.Column(db.String(30))  # 登录地址根据IP获得


class OperationLog(db.Model):
    """用户操作日志"""
    __tablename__ = "sys_op_logs"

    id = db.Column(db.Integer, primary_key=True, autoincrement=True)
    user_type = db.Column(db.Integer, default=0, nullable=False)  # 用户类型
    username = db.Column(db.String(50), nullable=False)  # 用户名
    op_time = db.Column(db.DateTime, nullable=False)  # 操作时间
    op_ip = db.Column(db.String(30))  # 操作IP
    remark = db.Column(db.String(200))  # 操作说明
