from flask_sqlalchemy import SQLAlchemy
from werkzeug.contrib.cache import SimpleCache
import logging

db = SQLAlchemy()

logger = logging.getLogger(__name__)

cache = SimpleCache()
