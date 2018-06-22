from flask import Blueprint

adminBp = Blueprint("adminBp", __name__)

from admin.action import index
