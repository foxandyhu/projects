from datetime import datetime


def get_time_longstr():
    """获得长格式的时间字符串精确到毫秒级"""
    
    return datetime.now().strftime("%Y%m%d%H%M%S%S%f")
