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
from actions import templates_action
