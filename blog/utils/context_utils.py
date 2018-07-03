from flask import g, session
import hashlib, os

PAGINATION = "pagination"
LOGIN_USER = "login_user"


def get_current_user_session():
    """得到当前登录管理员"""

    return get_from_session(LOGIN_USER)


def set_current_user_session(username):
    """设置当前登录管理员到Session"""

    put_to_session(LOGIN_USER, username)


def del_current_user_session():
    """删除当前登录管理员退出"""

    session.clear()


def put_to_session(key, value):
    """把数据放入session中"""

    session[key] = value


def get_from_session(key):
    """从session中获得值"""

    return session.get(key)


def del_from_session(key):
    if get_from_session(key):
        session.pop(key)


def put_to_g(key, value):
    """把数据放入g请求对象中"""

    g.setdefault(key, value)


def get_from_g(key):
    """从g请求对象中获得key对应的值"""

    return g.get(key)


def del_from_g(key):
    """从g请求对象中删除指定的key的值"""

    if g.get(key):
        g.pop(key)


def get_pagination():
    """获得分页对象"""

    return g.get(PAGINATION)


def del_pagination():
    """删除分页对象"""
    del_from_g(PAGINATION)


def get_tmpdir():
    """获得临时目录"""

    path = os.path.join(os.getcwd(), "static", "temp")
    if not os.path.exists(path):
        os.makedirs(path)
    return path + os.path.sep


def get_upload_face_dir():
    """获得头像上传绝对目录"""

    path = os.path.join(os.getcwd(), "static", "upload", "face")
    if not os.path.exists(path):
        os.makedirs(path)
    return path + os.path.sep


def get_upload_face_dir_rlt():
    """获得头像上传目录相对路径"""

    return "/static/upload/face/"


def get_upload_article_dir():
    """获得文章内容图片上传绝对目录"""

    path = os.path.join(os.getcwd(), "static", "upload", "article")
    if not os.path.exists(path):
        os.makedirs(path)
    return path + os.path.sep


def get_upload_article_dir_rlt():
    """获得文章内容图片上传相对路径"""

    return "/static/upload/article/"


def get_app_image_dir():
    """获得图片绝对目录"""

    path = os.path.join(os.getcwd(), "static", "images")
    if not os.path.exists(path):
        os.makedirs(path)
    return path + os.path.sep


def get_app_image_dir_rlt():
    """获得APP图片相对路径"""

    return "/static/images/"


def get_appdir():
    """获得APP根目录"""

    path = os.getcwd()
    return path + os.path.sep


def get_app_template_dir():
    """获得App模板绝对目录"""

    path = os.path.join(os.getcwd(), "templates")
    if not os.path.exists(path):
        os.makedirs(path)
    return path + os.path.sep


def get_md5(str=""):
    """MD5加密"""

    md5 = hashlib.md5()
    md5.update(str.encode(encoding="utf-8"))

    md5_str = md5.hexdigest()
    return md5_str
