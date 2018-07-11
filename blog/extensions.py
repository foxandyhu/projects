from flask_sqlalchemy import SQLAlchemy
from werkzeug.contrib.cache import RedisCache
import logging
from env_config import ConfigEnum

db = SQLAlchemy()

logger = logging.getLogger(__name__)

config = ConfigEnum.DEVELOPMENT

cache = RedisCache(host=config.REDIS_HOST, port=config.REDIS_PORT, key_prefix=config.REDIS_KEY_PREFIX,
                   db=config.REDIS_DB, password=config.REDIS_PWD)
