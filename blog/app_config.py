from flask import Flask
from env_config import ConfigEnum
from extensions import db


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

    app.register_blueprint(webBp, url_prefix="")
    app.register_blueprint(adminBp, url_prefix="/manage/")

