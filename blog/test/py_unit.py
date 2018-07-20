from flask import Flask
from services.flow_service import FlowReportService, FlowStatisticService
from datetime import datetime, date
from flask_sqlalchemy import SQLAlchemy
from env_config import ConfigEnum
import unittest,psutil,time
import os
from utils import context_utils,string_utils
from models.system_model import File


class T(unittest.TestCase):
    def setUp(self):
        self.app = Flask(__name__)
        db = SQLAlchemy()
        self.app.config.from_object(ConfigEnum.DEVELOPMENT)
        self.app.config["SQLALCHEMY_ECHO"] = True
        self.app_context = self.app.app_context()
        self.app_context.push()
        db.init_app(self.app)

    def test_a(self):
        # date = datetime.now()
        # days = FlowStatisticService.get_statistic_pre_days(date.date())
        # for day in days:
        #     results = FlowStatisticService.statistis_hour(day[0])
        #     for result in results:
        #         print("IP:", result[0], "  UV:", result[1], "  PV:", result[2], "  ST", result[3], "  value:", result[4])

        now = datetime.now()
        begin = date(now.year, 1, 1)
        end = date(now.year, 12, 31)
        result = FlowReportService.statistic_source(begin, end)
        print(result)

    def test_ps(self):
        total_befor = psutil.net_io_counters()
        time.sleep(1)
        total_after =psutil.net_io_counters()

        print(total_after.bytes_sent - total_befor.bytes_sent)
        print(total_after.bytes_recv - total_befor.bytes_recv)

        print("CPU:",psutil.cpu_percent())
        print("Mem:",psutil.virtual_memory())

    def test_file(self):
        path = "G:\\GIT\\blog\\templates" + os.sep
        files = os.listdir(path)
        for file in files:
            print("路径:",string_utils.bytes2human(os.path.getsize(path+file),0))
            if os.path.isdir(path+file):
                print(file+"是目录")
            else:
                print(file)

# if __name__ == '__main__':
#     unittest.main()
