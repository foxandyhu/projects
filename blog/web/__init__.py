from flask import Blueprint

webBp = Blueprint("webBp", __name__)

from web.actions import index
