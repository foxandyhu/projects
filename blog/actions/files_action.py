from actions import adminBp
from utils import json_utils, context_utils, date_utils
from flask import request


@adminBp.route("/file/upload/img.html", methods=["POST"])
def upload_img():
    """上传图片"""
    file = request.files.get("imgFile")
    path = date_utils.get_time_longstr() + "." + get_img_type(file.mimetype)
    if file:
        file.save(context_utils.get_tmpdir() + path)
    res = {
        "fullUrl": "/static/temp/" + path,
        "path": "/static/temp/" + path
    }
    return json_utils.to_json(res)


def get_img_type(type):
    """获得图片类型并返回后缀名称"""
    type_dir = {"image/gif": "gif", "image/png": "png", "image/jpeg": "jpg"}
    suffix = type_dir[type]
    if not type:
        suffix = type_dir["image/jpeg"]
    return suffix
