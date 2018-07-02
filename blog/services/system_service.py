from models import system_model
from utils import context_utils, pagination_utils, string_utils, file_utils
from extensions import db


class SystemService(object):
    """系统业务接口"""

    @staticmethod
    def get_sys_info():
        """获得系统基本信息"""

        result = system_model.SysSetting.query.first()
        return result

    @staticmethod
    def merge_sys_info(sys_info):
        """更新或添加系统基本信息"""

        info = SystemService.get_sys_info()
        if info:
            info.name = sys_info.name
            info.website = sys_info.website
            info.key_words = sys_info.key_words
            info.remark = sys_info.remark
            info.is_enable = sys_info.is_enable
            if info.logo != sys_info.logo:
                info.logo = sys_info.logo
                source = context_utils.get_appdir() + sys_info.logo  # LOGO源物理路径
                dest = context_utils.get_app_image_dir()+"logo.png"
                if sys_info.logo:
                    file_utils.move_file(source, dest)
        else:
            db.session.add(sys_info)
        db.session.commit()
