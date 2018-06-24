from flask import g
from flask import request
from exceptions import exception


def instantce_page(rows=10):
    """实例化一个分页对象"""

    page_no = 'pageNo' in request.form and request.form.get("pageNo")
    if not page_no:
        raise exception.PagerException("缺少分页参数")
    g.page_no = page_no
    g.rows = rows
