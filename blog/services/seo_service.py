from utils import context_utils, pagination_utils
from extensions import db, logger
from models.system_model import FriendLink


class FriendLinkService(object):
    """友情链接业务接口"""

    @staticmethod
    def get_friendlink_id(friendlink_id):
        """根据链接Id获得友情链接"""

        link = FriendLink.query.filter(FriendLink.id == friendlink_id).first()
        return link

    @staticmethod
    def get_friendlinks():
        """获得友情链接列表"""

        pagination = context_utils.get_pagination()
        if pagination:
            pagination = FriendLink.query.order_by(FriendLink.seq.asc()).paginate(pagination.page_no,
                                                                                  pagination.page_size)
            result = pagination_utils.get_pagination_sqlalchemy(pagination)
        else:
            result = FriendLink.query.all()
        return result

    @staticmethod
    def add_friendlink(link):
        """添加友情链接"""

        db.session.add(link)
        db.session.commit()

    @staticmethod
    def edit_friendlink(link):
        """编辑友情链接"""

        friendlink = FriendLinkService.get_friendlink_id(link.id)
        if not friendlink:
            logger.error("编辑友情链接失败,不存在ID为%d的链接" % link.id)
            raise Exception("该友情链接不存在!")

        friendlink.name = link.name
        friendlink.link = link.link
        friendlink.seq = link.seq
        db.session.commit()

    @staticmethod
    def del_friendlink(friendlink_id):
        """删除友情链接"""

        link = FriendLinkService.get_friendlink_id(friendlink_id)
        if link:
            db.session.delete(link)
            db.session.commit()
