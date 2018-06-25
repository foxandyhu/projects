class Serializable(object):
    """标识类是否可序列化"""
    pass


class ResponseData(Serializable):
    """响应的统一格式"""
    SUCCESS = 1  # 成功
    FAILED = 0  # 失败

    def __init__(self, status, message):
        self.status = status
        self.message = message

    def __str__(self):
        return "{status:%d,message:%s}" % (self.status, self.message)

    @staticmethod
    def get_success(message="操作成功!"):
        resp = ResponseData(ResponseData.SUCCESS, message)
        return resp

    @staticmethod
    def get_failed(message="操作失败!"):
        resp = ResponseData(ResponseData.FAILED, message)
        return resp
