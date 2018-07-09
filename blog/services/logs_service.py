from models.logs_model import LoginLogs
from utils import context_utils, pagination_utils, extends_utils
from extensions import db, logger
from datetime import datetime
import threading


class LoginLogsService(object):
    """用户登录业务接口"""

    @staticmethod
    def get_page_loginlogs():
        """返回分页对象"""

        pagination = context_utils.get_pagination()
        query = LoginLogs.query.order_by(LoginLogs.id.desc())
        pagination = query.paginate(pagination.page_no, pagination.page_size)
        pagination = pagination_utils.get_pagination_sqlalchemy(pagination)
        return pagination

    @staticmethod
    def add_loginlogs(loginlogs):
        """添加登录日志"""

        context = db.get_app().app_context()

        def do_save():
            try:
                city_json = extends_utils.get_city_ip(loginlogs.login_ip)
                city = None
                if city_json:
                    city = city_json.get("city")
                    if not city:
                        city = city_json.get("region")
                loginlogs.login_area = city
            except:
                logger.warn("记录登录日志,获取IP区域名称失败!")
            loginlogs.login_time = datetime.now()
            with context:
                db.session.add(loginlogs)
                db.session.commit()

        thread = threading.Thread(target=do_save)
        thread.start()
