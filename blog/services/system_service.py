from models import system_model
from utils import context_utils, file_utils
from extensions import db, cache


class SystemService(object):
    """系统业务接口"""

    @staticmethod
    def get_sys_info():
        """获得系统基本信息"""

        result = system_model.SysInfo.query.first()
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
                dest = context_utils.get_app_image_dir() + "logo.png"
                if sys_info.logo:
                    file_utils.move_file(source, dest)
        else:
            if sys_info.logo:
                source = context_utils.get_appdir() + sys_info.logo  # LOGO源物理路径
                dest = context_utils.get_app_image_dir() + "logo.png"
                sys_info.logo = context_utils.get_app_image_dir_rlt() + "logo.png"
            db.session.add(sys_info)
            if sys_info.logo:
                file_utils.move_file(source, dest)
        db.session.commit()
        cache.set("sysinfo", SystemService.get_sys_info(), 0)

    @staticmethod
    def get_sys_copyright():
        """获得系统版权信息"""

        result = system_model.SysCopyRight.query.first()
        return result

    @staticmethod
    def merge_sys_copyright(copyright):
        """更新或添加系统版权信息"""

        info = SystemService.get_sys_copyright()
        if info:
            info.icp = copyright.icp
            info.copyright = copyright.copyright
            info.organizer = copyright.organizer
            info.contacts = copyright.contacts
            info.phone = copyright.phone
            info.address = copyright.address
        else:
            db.session.add(copyright)
        db.session.commit()
        cache.set("copyright", SystemService.get_sys_copyright(), 0)

    @staticmethod
    def get_sys_nav_id(navbar_id):
        """根据ID查找导航栏"""

        result = system_model.SysNavigatorBar.query.filter(system_model.SysNavigatorBar.id == navbar_id).first()
        return result

    @staticmethod
    def add_sys_nav(navbar):
        """添加导航栏"""

        db.session.add(navbar)
        db.session.commit()

    @staticmethod
    def edit_sys_nav(navbar):
        """编辑导航栏"""

        bar = SystemService.get_sys_nav_id(navbar.id)
        if not bar:
            raise Exception("导航栏不存在!")
        bar.name = navbar.name
        bar.seq = navbar.seq
        bar.link = navbar.link
        bar.action = navbar.action
        db.session.commit()

    def del_sys_nav(navbar_id):
        """删除导航栏"""

        bar = SystemService.get_sys_nav_id(navbar_id)
        if bar:
            db.session.delete(bar)
            db.session.commit()

    @staticmethod
    def get_sys_navs():
        """查找所有的导航栏"""

        result = system_model.SysNavigatorBar.query.order_by(system_model.SysNavigatorBar.seq.asc()).all()
        return result