from datetime import datetime


def get_time_longstr():
    """获得长格式的时间字符串精确到毫秒级"""

    return datetime.now().strftime("%Y%m%d%H%M%S%S%f")


def parse_datetime_str(date):
    """日期时间转换为常用的格式字符串类型"""

    return date.strftime("%Y-%m-%d %H:%M:%S")


def parse_str_datetime(date_str):
    """将字符串转换为日期时间类型"""

    date = datetime.strptime(date_str, "%Y-%m-%d %H:%M:%S")
    return date
