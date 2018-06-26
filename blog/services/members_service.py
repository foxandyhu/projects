from models import members_model
from utils import context_utils, pagination_utils, string_utils, file_utils
from sqlalchemy import or_
from extensions import db


class MemberService(object):
    """用户业务接口"""

    @staticmethod
    def get_page_members():
        """返回分页对象"""

        pagination = context_utils.get_pagination()
        name = context_utils.get_from_g("name")
        is_verify = context_utils.get_from_g("verify")
        query = members_model.Member.query
        if is_verify is not None:
            query = query.filter(members_model.Member.is_verify == is_verify)
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

    def get_member_username(username):
        """根据用户名查找用户"""

        result = members_model.Member.query.filter(members_model.Member.username == username).first()
        return result

    @staticmethod
    def edit_member_enable(member_id):
        """修改会员状态"""

        member = MemberService.get_member(member_id)
        if not member:
            raise Exception("会员信息不存在")
        member.is_enable = not member.is_enable
        db.session.commit()

    @staticmethod
    def edit_member_verify(member_id):
        """修改会员审核状态"""

        member = MemberService.get_member(member_id)
        if not member:
            raise Exception("会员信息不存在")
        member.is_verify = not member.is_verify
        db.session.commit()

    @staticmethod
    def add_member(member):
        """添加会员"""

        m = MemberService.get_member_username(member.username)
        if m:
            raise Exception("用户名已存在!")

        member.salt = string_utils.get_random_str(5)
        member.password = context_utils.get_md5(member.salt + member.password)  # 密码混淆

        source = context_utils.get_appdir() + member.face  # 头像源物理路径
        dest = context_utils.get_upload_face_dir()

        member.face = context_utils.get_upload_face_dir_rlt() + file_utils.get_filename(source)

        db.session.add(member)
        db.session.commit()

        file_utils.move_file(source, dest)
