import string
import random


def get_random_str(length):
    """获得随机指定长度的字符串"""

    str_list = [random.choice(string.digits + string.ascii_letters) for i in range(length)]
    str = ''.join(str_list)
    return str


def bytes2human(n, precision=2):
    """直接转换为可读的大小单位"""

    symbols = ("K", "M", "G", "T", "P", "E", "Z", "Y")
    prefix = {}
    for i, s in enumerate(symbols):
        prefix[s] = 1 << (i + 1) * 10
    for s in reversed(symbols):
        if n >= prefix[s]:
            value = float(n) / prefix[s]
            return f"%.{precision}f %s" % (value, s)
    return f"%.{precision}f B" % (n)
