from flask import render_template
from actions import templateAdminBp


@templateAdminBp.route("/<tpl>.html")
def template_render(tpl):
    """公共的模板跳转"""
    return render_template("admin/" +tpl + ".html")


@templateAdminBp.route("/<dir>/<tpl>.html")
def template_render_dir(dir, tpl):
    """公共的模板跳转"""
    return render_template("admin/" + dir + "/" + tpl + ".html")
