package com.bfly.cms.service.impl;

import com.bfly.cms.dao.IFriendLinkDao;
import com.bfly.cms.entity.FriendLink;
import com.bfly.cms.service.IFriendLinkService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/11 16:56
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class FriendLinkServiceImpl extends BaseServiceImpl<FriendLink, Integer> implements IFriendLinkService {

    @Autowired
    private IFriendLinkDao friendLinkDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(FriendLink friendLink) {
        int maxSeq = friendLinkDao.getMaxSeq();
        friendLink.setSeq(++maxSeq);
        boolean flag = super.save(friendLink);
        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(FriendLink friendLink) {
        FriendLink link = get(friendLink.getId());
        Assert.notNull(link, "不存在的链接信息!");
        boolean flag = super.edit(friendLink);
        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sortFriendLink(int upId, int downId) {
        FriendLink upItem = get(upId);
        Assert.notNull(upItem, "不存在的友情链接信息!");

        FriendLink downItem = get(downId);
        Assert.notNull(downItem, "不存在的友情链接信息!");

        friendLinkDao.editFriendLinkSeq(upId, downItem.getSeq());
        friendLinkDao.editFriendLinkSeq(downId, upItem.getSeq());

        Cache cache = getCache();
        cache.clear();
    }

    @Override
    @Transactional
    public int remove(Integer... integers) {
        int count = super.remove(integers);
        return count;
    }
}
