from models import members_model
from utils import context_utils, pagination_utils
from sqlalchemy import or_
from extensions import db


class MemberService(object):
    """用户业务接口"""

    @staticmethod
    def get_page_members():
        """返回分页对象"""
        pagination = context_utils.get_pagination()
        name = context_utils.get_from_g("name")
        query = members_model.Member.query
        if name:
            rule = or_(members_model.Member.username.like("%" + name + "%"),
                       members_model.Member.nickname.like("%" + name + "%"))
            query = query.filter(rule)
        pagination = query.paginate(pagination.page_no, pagination.page_size)
        pagination = pagination_utils.get_pagination_sqlalchemy(pagination)
        return pagination

    @staticmethod
    def get_member(member_id):
        """根据会员ID查询会员信息"""

        result = members_model.Member.query.filter(members_model.Member.id == member_id).first()
        return result

    @staticmethod
    def edit_member_enable(member_id):
        """修改会员状态"""

        member = MemberService.get_member(member_id)
        if not member:
            raise Exception("会员信息不存在")
        member.is_enable =not member.is_enable
        db.session.commit()
