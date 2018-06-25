from flask import g

PAGINATION = "pagination"


def put_to_g(key, value):
    """把数据放入g请求对象中"""

    g.setdefault(key, value)


def get_from_g(key):
    """从g请求对象中获得key对应的值"""

    return g.get(key)


def get_pagination():
    """获得分页对象"""

    return g.get(PAGINATION)
