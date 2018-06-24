class PagerException(Exception):
    """分页异常"""

    def __init__(self, message):
        super().__init__(message)
