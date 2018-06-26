import string
import random


def get_random_str(length):
    """获得随机指定长度的字符串"""

    str_list = [random.choice(string.digits + string.ascii_letters) for i in range(length)]
    str = ''.join(str_list)
    return str
