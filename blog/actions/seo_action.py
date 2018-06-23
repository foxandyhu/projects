from flask import render_template
from actions import adminBp


@adminBp.route("/seo/friendlink.html")
def frienlink_list():
    """友情链接"""

    return render_template("admin/seo/friendlink_list.html")
