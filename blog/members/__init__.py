from flask import Blueprint

memberBp = Blueprint("memberBp", __name__)

from members.model import members
