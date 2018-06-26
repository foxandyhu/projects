from actions import adminBp
from flask import request
from utils import pagination_utils, json_utils, context_utils
from services.members_service import MemberService
from models import ResponseData, members_model
from models.forms import MembersForm
from datetime import datetime


@adminBp.route("/members/list.html")
def member_list():
    """用户管理"""

    pagination_utils.instantce_page()
    context_utils.put_to_g("name", 'name' in request.args and request.args.get("name"))
    pagination = MemberService.get_page_members()
    return json_utils.to_json(pagination)


@adminBp.route("/members/unverify.html")
def member_unverify_list():
    """未审核用户列表"""

    pagination_utils.instantce_page()
    context_utils.put_to_g("verify", False)
    context_utils.put_to_g("name", 'name' in request.args and request.args.get("name"))
    pagination = MemberService.get_page_members()
    return json_utils.to_json(pagination)


@adminBp.route("/members/enable-<int:member_id>.html")
def enable_member(member_id):
    """禁启用会员状态"""

    MemberService.edit_member_enable(member_id)
    return json_utils.to_json(ResponseData.get_success())


@adminBp.route("/members/verify-<int:member_id>.html")
def verify_member(member_id):
    """审核通过会员"""

    MemberService.edit_member_verify(member_id)
    return json_utils.to_json(ResponseData.get_success())


@adminBp.route("/members/add.html", methods=["POST"])
def add_member():
    """新增用户"""

    form = MembersForm(request.form)
    if not form.validate():
        raise Exception(form.errors)

    member = members_model.Member()
    member.username = form.username.data
    member.nickname = form.password.data
    member.is_verify = False
    member.is_enable = not request.form.get("is_enable") is None
    member.reg_time = datetime.now()
    member.reg_ip = request.remote_addr
    member.password = form.password.data
    member.face = request.form.get("face")

    MemberService.add_member(member)

    return json_utils.to_json(ResponseData.get_success("新增成功"))
