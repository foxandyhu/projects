package com.bfly.cms.service;

import com.bfly.cms.entity.Ad;
import com.bfly.core.base.service.IBaseService;

/**
 * 广告管理业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/11 17:09
 */
public interface IAdService extends IBaseService<Ad, Integer> {

    /**
     * 禁用已经过期的广告
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/9 23:59
     */
    void disableExpiredAds();

    /**
     * 启用展示日期开始的广告
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/25 14:17
     */
    void enabledUnExpireAds();
}
