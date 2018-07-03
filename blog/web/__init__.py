from flask import Blueprint
from io import BytesIO
from utils import captch_utils, context_utils
from flask import make_response
from extensions import cache
from services import system_service

webBp = Blueprint("webBp", __name__)

from web.actions import index


@webBp.app_context_processor
def context_processor_data():
    """处理模板公共数据"""

    SYSINFO, COPYRIGHT = "sysinfo", "copyright"
    sysinfo = cache.get(SYSINFO)
    if not sysinfo:
        sysinfo = system_service.SystemService.get_sys_info()
        cache.set(SYSINFO, sysinfo, 0)

    copyright = cache.get(COPYRIGHT)
    if not copyright:
        copyright = system_service.SystemService.get_sys_copyright()
        cache.set(COPYRIGHT, copyright, 0)

    return dict(sysinfo=sysinfo, copyright=copyright)


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
