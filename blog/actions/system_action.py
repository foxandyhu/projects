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
            sys_info = system_model.SysSetting()
        return json_utils.to_json(sys_info)
    else:
        form = forms.SysInfoForm(request.form)
        if not form.validate():
            raise Exception(form.errors)

        info = system_model.SysSetting()
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


@adminBp.route("/system/web_template.html")
def web_template():
    """网站模板设置"""

    return render_template("admin/system/web_template.html")


@adminBp.route("/system/tasks.html")
def system_tasks():
    """系统任务"""

    return render_template("admin/system/tasks.html")


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
