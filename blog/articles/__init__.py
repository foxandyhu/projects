from flask import Blueprint

articleBp = Blueprint("articleBp", __name__)

from articles.model import articles
