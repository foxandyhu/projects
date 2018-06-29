from flask import Blueprint

adminBp = Blueprint("adminBp", __name__)
templateAdminBp = Blueprint("templateAdminBp", __name__)

from actions import manage_action
from actions import system_action
from actions import members_action
from actions import articles_action
from actions import seo_action
from actions import users_action
from actions import logs_action
from actions import templates_action, files_action
from utils import json_utils

@adminBp.errorhandler(Exception)
def admin_error(e):
    message = json_utils.to_json(e.args[0])
    return message, 500
