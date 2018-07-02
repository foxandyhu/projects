from flask import Blueprint
from io import BytesIO
from utils import captch_utils, context_utils
from flask import make_response

webBp = Blueprint("webBp", __name__)

from web.actions import index


@webBp.route("/captch.html")
def get_captch():
    """生成验证码响应输出"""

    image, code = captch_utils.get_captch()

    buf = BytesIO()  # 图片以二进制形式写入
    image.save(buf, 'png')
    buf_str = buf.getvalue()

    response = make_response(buf_str)  # 把buf_str作为response返回前端，并设置首部字段
    response.headers['Content-Type'] = 'image/png'
    context_utils.put_to_session("captch", code)  # 将验证码字符串储存在session中
    return response
