package com.bfly.cms.service;

import com.bfly.cms.entity.City;
import com.bfly.core.base.service.IBaseService;

import java.util.List;

/**
 * 城市管理接口
 *
 * @author andy_hulibo@163.com
 * @date 2020/8/26 11:24
 */
public interface ICityService extends IBaseService<City, Integer> {

    /**
     * 根据拼音查找城市
     *
     * @author andy_hulibo@163.com
     * @date 2020/8/26 11:25
     */
    List<City> getCity(String pinyin, int level);
}
