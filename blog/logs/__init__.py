from flask import Blueprint

logBp = Blueprint("logBp", __name__)

from logs.model import logs