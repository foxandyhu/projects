from flask import Flask
from services.flow_service import FlowReportService, FlowStatisticService
from datetime import datetime, date
from flask_sqlalchemy import SQLAlchemy
from env_config import ConfigEnum
import unittest


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


if __name__ == '__main__':
    unittest.main()
