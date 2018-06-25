from actions import adminBp
from flask import request
from utils import pagination_utils, json_utils, context_utils
from services.members_service import MemberService
from models import ResponseData


@adminBp.route("/members/list.html")
def member_list():
    """用户管理"""

    pagination_utils.instantce_page(2)
    context_utils.put_to_g("name", 'name' in request.args and request.args.get("name"))
    pagination = MemberService.get_page_members()
    return json_utils.to_json(pagination)


@adminBp.route("/members/unverify.html")
def member_unverify_list():
    """未审核用户列表"""

    pagination_utils.instantce_page(2)
    context_utils.put_to_g("verify", False)
    context_utils.put_to_g("name", 'name' in request.args and request.args.get("name"))
    pagination = MemberService.get_page_members()
    return json_utils.to_json(pagination)


@adminBp.route("/members/enable-<int:member_id>.html")
def enable_member(member_id):
    """禁启用会员状态"""

    MemberService.edit_member_enable(member_id)
    return json_utils.to_json(ResponseData.get_success())
