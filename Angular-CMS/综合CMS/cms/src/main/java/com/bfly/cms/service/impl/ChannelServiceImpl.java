package com.bfly.cms.service.impl;

import com.bfly.cms.dao.IChannelDao;
import com.bfly.cms.entity.Channel;
import com.bfly.cms.enums.SysError;
import com.bfly.cms.service.IChannelService;
import com.bfly.common.reflect.ClassUtils;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.cache.EhCacheUtil;
import com.bfly.core.exception.WsResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2019/8/4 15:10
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class ChannelServiceImpl extends BaseServiceImpl<Channel, Integer> implements IChannelService {

    @Autowired
    private IChannelDao channelDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Channel channel) {
        if (!channel.getPath().endsWith(".html")) {
            throw new WsResponseException(SysError.PARAM_ERROR, "必须已.html结尾");
        }
        Channel dbChannel = channelDao.getChannelByPath(channel.getPath());
        Assert.isTrue(dbChannel == null, "栏目路径已存在!");

        dbChannel = channelDao.getChannelByAlias(channel.getAlias());
        Assert.isTrue(dbChannel == null, "栏目路径已存在!");

        int maxSeq = channelDao.getMaxSeq();
        channel.setSeq(++maxSeq);
        boolean flag = super.save(channel);

        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(Channel channel) {
        Channel cl = get(channel.getId());
        Assert.notNull(cl, "不存在的栏目信息!");

        //修改了栏目路径
        if (!channel.getPath().endsWith(".html")) {
            throw new WsResponseException(SysError.PARAM_ERROR, "必须已.html结尾");
        }
        if (!cl.getPath().equals(channel.getPath())) {
            Channel dbChannel = channelDao.getChannelByPath(channel.getPath());
            Assert.isTrue(dbChannel == null, "栏目路径已存在!");
        }
        if (!cl.getAlias().equals(channel.getAlias())) {
            Channel dbChannel = channelDao.getChannelByAlias(channel.getAlias());
            Assert.isTrue(dbChannel == null, "栏目路径已存在!");
        }

        channel.setSeq(cl.getSeq());
        boolean flag = super.edit(channel);

        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sortChannel(int upChannelId, int downChannelId) {
        Channel upChannel = get(upChannelId);
        Assert.notNull(upChannel, "不存在的栏目信息!");

        Channel downChannel = get(downChannelId);
        Assert.notNull(downChannel, "不存在的栏目信息!");

        channelDao.editChannelSeq(upChannelId, downChannel.getSeq());
        channelDao.editChannelSeq(downChannelId, upChannel.getSeq());

        Cache cache = getCache();
        cache.clear();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int remove(Integer... ids) {
        Cache cache = getCache();
        cache.clear();
        return channelDao.deleteChannel(ids);
    }

    @Override
    public Channel getChannelByPath(String path) {
        Cache cache = getCache();
        Channel channel = cache.get(path, Channel.class);
        if (channel == null) {
            channel = channelDao.getChannelByPath(path);
            cache.put(path, channel);
        }
        return channel;
    }

    @Override
    public Channel getChannelByAlias(String alias) {
        Cache cache = getCache();
        Channel channel = cache.get(alias, Channel.class);
        if (channel == null) {
            channel = channelDao.getChannelByAlias(alias);
            cache.put(alias, channel);
        }
        return channel;
    }
}
