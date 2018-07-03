from flask import render_template, request
from actions import adminBp
from services import system_service
from utils import json_utils, context_utils, file_utils
from models import ResponseData, forms, system_model


@adminBp.route("/system/web_setting.html", methods=["GET", "POST"])
def web_setting():
    """网站设置"""

    if request.method == "GET":
        sys_info = system_service.SystemService.get_sys_info()
        if not sys_info:
            sys_info = system_model.SysInfo()
        return json_utils.to_json(sys_info)
    else:
        form = forms.SysInfoForm(request.form)
        if not form.validate():
            raise Exception(form.errors)

        info = system_model.SysInfo()
        info.name = form.name.data
        info.website = form.website.data
        info.is_enable = not request.form.get("is_enable") is None
        info.key_words = request.form.get("keywords")
        info.remark = request.form.get("remark")
        info.logo = request.form.get("logo")

        system_service.SystemService.merge_sys_info(info)
        return json_utils.to_json(ResponseData.get_success())


@adminBp.route("/system/web_copyright.html", methods=["GET", "POST"])
def web_copyright():
    """网站版权"""

    if request.method == "GET":
        copyright = system_service.SystemService.get_sys_copyright()
        if not copyright:
            copyright = system_model.SysCopyRight()
        return json_utils.to_json(copyright)
    else:
        info = system_model.SysCopyRight()
        info.icp = request.form.get("icp")
        info.copyright = request.form.get("copyright")
        info.organizer = request.form.get("organizer")
        info.contacts = request.form.get("contacts")
        info.phone = request.form.get("phone")
        info.address = request.form.get("address")

        system_service.SystemService.merge_sys_copyright(info)
        return json_utils.to_json(ResponseData.get_success())


@adminBp.route("/system/navbar.html")
def get_navbars():
    """网站导航栏设置"""

    navbars = system_service.SystemService.get_sys_navs()
    return json_utils.to_json(navbars)


@adminBp.route("/system/navbar/add.html", methods=["POST"])
def add_navbar():
    """添加导航栏"""

    navbar = system_model.SysNavigatorBar()
    navbar.name = request.form.get("name")
    if not navbar.name:
        raise Exception("导航栏名称为空!")

    navbar.link = request.form.get("link")
    navbar.action = request.form.get("action")
    seq = request.form.get("seq")
    seq = seq if seq else 0
    if str.isdigit(seq):
        navbar.seq = int(seq)
    system_service.SystemService.add_sys_nav(navbar)
    return json_utils.to_json(ResponseData.get_success())


@adminBp.route("system/navbar/del/<int:navbar_id>.html")
def del_navbar(navbar_id):
    """删除导航栏"""

    system_service.SystemService.del_sys_nav(navbar_id)
    return json_utils.to_json(ResponseData.get_success())


@adminBp.route("/system/about.html", methods=["GET", "POST"])
def system_about():
    """关于我们"""

    path = context_utils.get_app_template_dir()
    path = path + "about.tpl"
    if request.method == "GET":
        result = ""
        if file_utils.exists(path):
            with open(file=path, mode="r", encoding="utf8") as f:
                for line in f:
                    result += line
        return result

    about = request.form.get("content")
    with open(file=path, mode="w", encoding="utf8") as file:
        file.writelines(about)
    return json_utils.to_json(ResponseData.get_success())


@adminBp.route("/system/contact.html", methods=["GET", "POST"])
def system_contact():
    """联系我们"""

    path = context_utils.get_app_template_dir()
    path = path + "contact.tpl"
    if request.method == "GET":
        result = ""
        if file_utils.exists(path):
            with open(file=path, mode="r", encoding="utf8") as f:
                for line in f:
                    result += line
        return result

    about = request.form.get("content")
    with open(file=path, mode="w", encoding="utf8") as file:
        file.writelines(about)
    return json_utils.to_json(ResponseData.get_success())
