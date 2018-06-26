from utils import context_utils
from flask import request
from models import Serializable


class Pagination(Serializable):
    """分页对象"""

    def __init__(self, page_no=1, page_size=10):
        self.page_no = page_no
        self.page_size = page_size
        self.total = 0
        self.page_count = 0
        self.items = None

    def get_page_count(self):
        """获得最大页码"""
        self.total = int((self.total + self.page_size - 1) / self.page_size)
        return self.total

    def get_pre_page(self):
        """获得上一页码"""
        if self.page_no <= 1:
            return self.page_no
        return self.page_no - 1

    def get_next_page(self):
        """获得下一页码"""
        if self.page_no >= self.get_page_count():
            return self.get_page_count()
        return self.page_no + 1

    def __str__(self):
        return "{page_no:%d,page_size:%d,total:%d,pre_page:%d,next_page:%d,page_count:%d,items:%s}" % (
            self.page_no, self.page_size, self.total, self.get_pre_page(), self.get_next_page(),
            self.get_page_count(), self.items)


def instantce_page(page_size=10):
    """实例化一个分页对象"""

    page_no = request.args.get("pageNo") if request.method == 'GET' and 'pageNo' in request.args else request.form.get(
        "pageNo") and 'pageNo' in request.form
    if not page_no:
        page_no = 1
    page_no = int(page_no)
    page = Pagination(page_no, page_size)
    context_utils.put_to_g(context_utils.PAGINATION, page)


def get_pagination_sqlalchemy(pagination):
    """通过flask_sqlalchemy的Pagination对象构建自定义的分页对象"""

    page = Pagination(pagination.page, pagination.per_page)
    page.items = pagination.items
    page.total = pagination.total
    page.get_page_count()
    return page
