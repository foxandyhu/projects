from flask import json
from models import Serializable
import datetime


class MyEncoder(json.JSONEncoder):
    """自定义的Json编码器"""

    def default(self, o):
        if isinstance(o, Serializable):
            __dict = {}
            for name, value in vars(o).items():
                if name.startswith("_"):
                    continue
                if isinstance(value, Serializable):
                    value = json.dumps(value, cls=MyEncoder)
                elif isinstance(value, datetime.datetime):
                    value = value.strftime("%Y-%m-%d %H:%M:%S")
                __dict[name] = value
            return __dict


def to_json(obj):
    """对象转JSON"""

    return json.dumps(obj, cls=MyEncoder)


def to_json_str(str):
    """字符串转为json"""

    return json.loads(str)
