from flask import render_template, request
from actions import adminBp
from services import system_service
from utils import json_utils, context_utils, file_utils, string_utils, date_utils
from models import ResponseData, forms, system_model
from models.system_model import File
import copy, os


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
    """获得网站导航栏"""

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


@adminBp.route("/system/navbar/edit.html", methods=["POST"])
def edit_navbar():
    """编辑导航栏"""

    navbar = system_model.SysNavigatorBar()
    navbar.name = request.form.get("name")
    if not navbar.name:
        raise Exception("导航栏名称为空!")

    navbar.id = request.form.get("id")
    navbar.link = request.form.get("link")
    navbar.action = request.form.get("action")
    seq = request.form.get("seq")
    seq = seq if seq else 0
    if str.isdigit(seq):
        navbar.seq = int(seq)
    system_service.SystemService.edit_sys_nav(navbar)
    return json_utils.to_json(ResponseData.get_success())


@adminBp.route("system/navbar/del/<int:navbar_id>.html")
def del_navbar(navbar_id):
    """删除导航栏"""

    system_service.SystemService.del_sys_nav(navbar_id)
    return json_utils.to_json(ResponseData.get_success())


@adminBp.route("/system/navbar/tohtml.html")
def create_navbar_html():
    """生成静态模板"""

    navbars = system_service.SystemService.get_sys_navs()
    html = render_template("header.html", navbars=navbars)

    path = context_utils.get_app_template_dir()
    path = path + "header.tpl"
    with open(file=path, mode="w", encoding="utf8") as file:
        file.writelines(html)

    return json_utils.to_json(ResponseData.get_success())


@adminBp.route("/system/banner.html")
def get_banners():
    """获得网站banner"""

    banners = system_service.SystemService.get_sys_banners()
    return json_utils.to_json(banners)


@adminBp.route("/system/banner/add.html", methods=["POST"])
def add_banner():
    """添加Banner"""

    banner = system_model.SysBanner()
    banner.title = request.form.get("title")
    banner.link = request.form.get("link")
    banner.action = request.form.get("action")
    seq = request.form.get("seq")
    seq = seq if seq else 0
    if str.isdigit(seq):
        banner.seq = int(seq)
    system_service.SystemService.add_sys_banner(banner)
    return json_utils.to_json(ResponseData.get_success())


@adminBp.route("/system/banner/upload.html", methods=["POST"])
def upload_banner_logo():
    """上传banner图片"""

    banner_id = request.form.get("banner_id")
    logo = request.form.get("logo")

    banner = system_service.SystemService.get_sys_banner_id(banner_id)

    b = copy.copy(banner)
    b.logo = logo
    system_service.SystemService.edit_sys_banner(b)
    return json_utils.to_json(ResponseData.get_success())


@adminBp.route("/system/banner/edit.html", methods=["POST"])
def edit_banner():
    """编辑导航栏"""

    banner = system_model.SysBanner()
    banner.id = request.form.get("id")
    banner.title = request.form.get("title")
    banner.link = request.form.get("link")
    banner.action = request.form.get("action")
    banner.logo = request.form.get("logo")
    seq = request.form.get("seq")
    seq = seq if seq else 0
    if str.isdigit(seq):
        banner.seq = int(seq)

    system_service.SystemService.edit_sys_banner(banner)
    return json_utils.to_json(ResponseData.get_success())


@adminBp.route("/system/banner/del/<int:banner_id>.html")
def del_banner(banner_id):
    """删除Banner"""

    system_service.SystemService.del_sys_banner(banner_id)
    return json_utils.to_json(ResponseData.get_success())


@adminBp.route("/system/banner/tohtml.html")
def create_banner_html():
    """生成静态模板"""

    banners = system_service.SystemService.get_sys_banners()
    html = render_template("banner.html", banners=banners)

    path = context_utils.get_app_template_dir()
    path = path + "banner.tpl"
    with open(file=path, mode="w", encoding="utf8") as file:
        file.writelines(html)

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


@adminBp.route("/system/page/template.html")
def system_page_template():
    """返回系统模板目录文件"""

    rel_path = request.args.get("path") or os.sep
    path = context_utils.get_app_template_dir() + rel_path
    files = os.listdir(path)
    result = list(map(lambda file: File(file, string_utils.bytes2human(os.path.getsize(path + file), 0),
                                        date_utils.parse_timestamp_datetime(
                                            os.path.getmtime(os.path.abspath(path + file))),
                                        File.DIR if os.path.isdir(os.path.abspath(path + file)) else File.FILE,
                                        f"{rel_path}{file}{os.sep}"),
                      files))
    return json_utils.to_json(result)


@adminBp.route("/system/page/rename.html")
def system_page_rename():
    """修改文件名称"""

    path = request.args.get("path")
    nname = request.args.get("nname")
    flag = "true" == request.args.get("flag")

    if flag:
        path = context_utils.get_app_template_dir() + path
    else:
        path = context_utils.get_app_static_dir() + path
    path = os.path.normpath(path)
    nname = os.path.join(os.path.dirname(path), nname)
    file_utils.move_file(path, nname)
    return json_utils.to_json(ResponseData.get_success())


@adminBp.route("/system/page/del.html")
def del_system_page():
    """删除文件"""

    path = request.args.get("path")
    if path == "/":
        raise Exception("根路径不能删除!")
    flag = "true" == request.args.get("flag")

    if flag:
        path = context_utils.get_app_template_dir() + path
    else:
        path = context_utils.get_app_static_dir() + path
    path = os.path.normpath(path)
    if path != context_utils.get_app_static_dir() and path != context_utils.get_app_template_dir():
        file_utils.del_file(path)
    return json_utils.to_json(ResponseData.get_success())


@adminBp.route("/system/page/mkdir.html")
def create_new_dir():
    """新建目录"""

    path = request.args.get("path")
    dir_name = request.args.get("dirname")
    flag = "true" == request.args.get("flag")

    if flag:
        path = context_utils.get_app_template_dir() + path
    else:
        path = context_utils.get_app_static_dir() + path
    path = os.path.normpath(path + os.sep + dir_name)
    file_utils.mkdir(path)
    return json_utils.to_json(ResponseData.get_success())
