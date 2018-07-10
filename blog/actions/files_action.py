from actions import adminBp
from utils import json_utils, context_utils, date_utils, file_utils
from flask import request


def save_file(file, path, target):
    if file:
        file.save(target)
    res = {
        "fullUrl": "/static/temp/" + path,
        "path": "/static/temp/" + path
    }
    return json_utils.to_json(res)


@adminBp.route("/file/upload.html", methods=["POST"])
def upload_file():
    """上传图片"""

    file = request.files.get("file")
    path = date_utils.get_time_longstr() + file_utils.get_file_suffix(file.filename)
    target = context_utils.get_tmpdir() + path
    return save_file(file, path, target)


@adminBp.route("/file/upload/img.html", methods=["POST"])
def upload_img():
    """上传图片"""

    file = request.files.get("imgFile")
    path = date_utils.get_time_longstr() + "." + get_img_type(file.mimetype)
    target = context_utils.get_tmpdir() + path
    return save_file(file, path, target)


@adminBp.route("/file/upload/editor.html", methods=["POST"])
def upload_img_editor():
    """编辑器上传图片"""

    file = request.files.get("imgFile")
    path = date_utils.get_time_longstr() + "." + get_img_type(file.mimetype)
    if file:
        file.save(context_utils.get_upload_article_dir() + path)
    res = {
        "error": 0,
        "fullUrl": "/static/upload/article/" + path,
        "path": "/static/upload/article/" + path
    }
    return json_utils.to_json(res)


def get_img_type(type):
    """获得图片类型并返回后缀名称"""

    type_dir = {"image/gif": "gif", "image/png": "png", "image/jpeg": "jpg"}
    suffix = type_dir[type]
    if not type:
        suffix = type_dir["image/jpeg"]
    return suffix
