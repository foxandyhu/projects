from urllib.request import urlopen
from urllib.parse import urlencode
from utils import json_utils
from datetime import datetime


def get_city_ip(ip):
    """根据IP获得城市"""

    ip_url = "http://ip.taobao.com/service/getIpInfo.php?ip=" + ip
    content = get_net_request(ip_url)
    if not content:
        return None

    content = json_utils.to_json_str(content)
    if "code" not in content or content.get("code") != 0:
        return None
    json = content.get("data")
    return dict(country=json.get("country"), area=json.get("area"), region=json.get("region"), city=json.get("city"))


def get_city_weather(name):
    """获取城市天气信息"""

    location = urlencode({"location": name})
    weather_url = "http://api.map.baidu.com/telematics/v3/weather?output=json&ak=1LrIn254h4UEd72vrMVFBsFf&"+location

    content = get_net_request(weather_url)
    if not content:
        return None
    content = json_utils.to_json_str(content)
    if "status" not in content or content.get("status") != "success":
        return None
    json = content.get("results")
    json = json[0]
    json = json.get("weather_data")

    json = json[0]
    pic = json.get("nightPictureUrl")
    hour = int(datetime.now().strftime("%H"))

    if hour >= 6 and hour <= 18:
        pic = json.get("dayPictureUrl")

    return dict(city=name, weather=json.get("weather"), date=json.get("date"), pic=pic)


def get_net_request(url):
    """远程访问url请求"""

    content = urlopen(url).read()
    content = content.decode("utf-8")
    return content
