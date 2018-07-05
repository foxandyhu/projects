from flask import Flask
from env_config import ConfigEnum
from extensions import db
from services import system_service


def create_app(config=None):
    """创建Flask APP实例"""

    app = Flask(__name__)
    configure_app_env(app, config)
    configure_app_db(app)
    configure_app_logs(app)
    configure_app_blueprints(app)
    return app


def configure_app_env(app, config):
    """配置Flask的运行环境"""

    if not config:
        config = ConfigEnum.DEVELOPMENT
    app.config.from_object(config)


def configure_app_db(app):
    """配置APP 应用的数据库"""

    db.init_app(app)


def configure_app_logs(app):
    """配置日志"""

    import logging
    from logging import StreamHandler
    from logging.handlers import TimedRotatingFileHandler
    from extensions import logger

    if app.config.get("DEBUG"):
        handler = StreamHandler()
        handler.setLevel(logging.DEBUG)
    else:
        handler = TimedRotatingFileHandler("blog.log", 'D', 1, 10)
        handler.setLevel(logging.INFO)

    logger.addHandler(handler)


def configure_app_blueprints(app):
    """配置系统蓝图"""
    from web import webBp
    from actions import adminBp
    from actions import templateAdminBp

    app.register_blueprint(webBp, url_prefix="")
    app.register_blueprint(adminBp, url_prefix="/manage/")
    app.register_blueprint(templateAdminBp, url_prefix="/templates/admin")


def load_server_config():
    """加载配置WEB服务"""

    sys_info = system_service.SystemService.get_sys_info()
    if sys_info:
        SysServer.get_instance().server_enable = sys_info.is_enable


class SysServer(object):
    _instance = None

    def __init__(self):
        self.server_enable = False  # 网站是否启用

    def __new__(cls, *args, **kwargs):
        if cls._instance is None:
            cls._instance = super(SysServer, cls).__new__(cls, **args, **kwargs)
        return cls._instance

    @staticmethod
    def get_instance():
        return SysServer._instance
