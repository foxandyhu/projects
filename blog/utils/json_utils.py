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
    return json.dumps(obj, cls=MyEncoder)
