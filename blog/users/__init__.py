from flask import Blueprint

userBp = Blueprint("userBp", __name__)

from users.model import users
