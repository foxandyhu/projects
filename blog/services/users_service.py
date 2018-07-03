from models import users_model
from utils import context_utils, pagination_utils, string_utils, file_utils
from extensions import db


class UserService(object):
    """用户业务接口"""

    @staticmethod
    def login(username, password):
        """管理员登录"""
        user = UserService.get_user_username(username)
        if not user:
            raise Exception("用户名或密码错误!")
        pwd = context_utils.get_md5(user.salt + password)
        if user.password != pwd:
            raise Exception("用户名或密码错误!")
        return user

    @staticmethod
    def get_page_users():
        """返回分页对象"""

        pagination = context_utils.get_pagination()
        query = users_model.User.query.order_by(users_model.User.id.desc())
        pagination = query.paginate(pagination.page_no, pagination.page_size)
        pagination = pagination_utils.get_pagination_sqlalchemy(pagination)
        return pagination

    @staticmethod
    def get_user(user_id):
        """根据管理员D查询管理员信息"""

        result = users_model.User.query.filter(users_model.User.id == user_id).first()
        return result

    def get_user_username(username):
        """根据用户名查找管理员"""

        result = users_model.User.query.filter(users_model.User.username == username).first()
        return result

    @staticmethod
    def edit_user_enable(user_id):
        """修改管理员状态"""

        user = UserService.get_user(user_id)
        if not user:
            raise Exception("用户信息不存在")
        user.is_enable = not user.is_enable
        db.session.commit()

    @staticmethod
    def edit_user_pwd(user_id, pwd):
        """修改管理员状态"""

        user = UserService.get_user(user_id)
        if not user:
            raise Exception("用户信息不存在")
        if not pwd:
            raise Exception("密码为空!")
        user.salt = string_utils.get_random_str(5)
        user.password = context_utils.get_md5(user.salt + pwd)  # 密码混淆
        db.session.commit()

    @staticmethod
    def add_user(user):
        """添加管理员"""

        m = UserService.get_user_username(user.username)
        if m:
            raise Exception("用户名已存在!")

        user.salt = string_utils.get_random_str(5)
        user.password = context_utils.get_md5(user.salt + user.password)  # 密码混淆

        source = context_utils.get_appdir() + user.face  # 头像源物理路径
        dest = context_utils.get_upload_face_dir()

        user.face = context_utils.get_upload_face_dir_rlt() + file_utils.get_filename(source)

        db.session.add(user)
        db.session.commit()

        file_utils.move_file(source, dest)
