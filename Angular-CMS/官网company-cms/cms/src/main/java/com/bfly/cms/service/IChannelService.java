package com.bfly.cms.service;

import com.bfly.cms.entity.Channel;
import com.bfly.core.base.service.IBaseService;

/**
 * 栏目Service
 *
 * @author andy_hulibo@163.com
 * @date 2019/8/4 15:09
 */
public interface IChannelService extends IBaseService<Channel, Integer> {

    /**
     * 栏目排序
     *
     * @param downId 下移Id
     * @param upId   上移ID
     * @author andy_hulibo@163.com
     * @date 2019/8/4 15:21
     */
    void sortChannel(int upId, int downId);

    /**
     * 根据栏目路径查询栏目对象
     *
     * @param path
     * @return Channel
     * @author andy_hulibo@163.com
     * @date 2019/8/19 16:43
     */
    Channel getChannelByPath(String path);

    /**
     * 根据栏目的别名查找栏目对象
     * @author andy_hulibo@163.com
     * @date 2020/6/20 22:10
     */
    Channel getChannelByAlias(String alias);
}
