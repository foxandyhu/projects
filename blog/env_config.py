class BaseConfig(object):
    """默认配置基类"""

    CSRF_ENABLED = True
    JSON_AS_ASCII = False
    SECRET_KEY = "f61e5028a88794ce00e0865c1f04967a5af4833f5c912d269022d6b3999459a2"


class DevelopmentConfig(BaseConfig):
    """开发环境配置类"""
    SQLALCHEMY_DATABASE_URI = "mysql+pymysql://root:root@127.0.0.1:3306/blog"
    SQLALCHEMY_ECHO = True
    SQLALCHEMY_POOL_SIZE = 32
    SQLALCHEMY_TRACK_MODIFICATIONS = True
    DEBUG = True
    TESTING = True


class ProductConfig(BaseConfig):
    """生产环境配置类"""

    SQLALCHEMY_DATABASE_URI = "mysql://root:jsrroot@113.106.48.159:3306/blog"
    SQLALCHEMY_ECHO = False
    SQLALCHEMY_POOL_SIZE = 32
    SQLALCHEMY_TRACK_MODIFICATIONS = True
    DEBUG = False
    TESTING = False


class ConfigEnum:
    DEVELOPMENT = DevelopmentConfig
    PRODUCT = ProductConfig
