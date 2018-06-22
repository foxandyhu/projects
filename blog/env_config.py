import binascii, os


class BaseConfig(object):
    """默认配置基类"""

    DEBUG = True
    TESTING = True
    CSRF_ENABLED = True
    SECRET_KEY = binascii.hexlify(os.urandom(32))


class DevelopmentConfig(BaseConfig):
    """开发环境配置类"""
    SQLALCHEMY_DATABASE_URI = "mysql+pymysql://root:root@127.0.0.1:3306/blog"
    SQLALCHEMY_ECHO = True
    SQLALCHEMY_POOL_SIZE = 32
    SQLALCHEMY_TRACK_MODIFICATIONS = True


class ProductConfig(BaseConfig):
    """生产环境配置类"""

    SQLALCHEMY_DATABASE_URI = "mysql://root:root@127.0.0.1:3306/blog"
    SQLALCHEMY_ECHO = True
    SQLALCHEMY_POOL_SIZE = 32
    SQLALCHEMY_TRACK_MODIFICATIONS = True


class ConfigEnum:
    DEVELOPMENT = DevelopmentConfig
    PRODUCT = ProductConfig
