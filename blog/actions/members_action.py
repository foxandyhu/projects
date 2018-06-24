from actions import adminBp
from flask import render_template
import utils
from services.members_service import MemberService


@adminBp.route("/members/list.html")
def member_list():
    """用户管理"""

    utils.instantce_page()
    members=MemberService.get_all_members()
    return render_template("admin/members/member_list.html")


@adminBp.route("/members/unverify.html")
def member_unverify_list():
    """未审核用户列表"""

    return render_template("admin/members/member_list.html")