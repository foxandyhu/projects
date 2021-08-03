package com.bfly.cms.service.impl;

import com.bfly.cms.dao.ICityDao;
import com.bfly.cms.entity.City;
import com.bfly.cms.service.ICityService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2020/8/26 11:26
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class CityServiceImpl extends BaseServiceImpl<City, Integer> implements ICityService {

    @Autowired
    private ICityDao cityDao;

    @Override
    public List<City> getCity(String pinyin, int level) {
        Cache cache = getCache();
        String key = pinyin + "_" + level;
        List<City> cities = cache.get(key, List.class);
        if (CollectionUtils.isEmpty(cities)) {
            cities = cityDao.getCity(pinyin, level);
            if (CollectionUtils.isNotEmpty(cities)) {
                cache.put(key, cities);
            }
        }
        return cities;
    }
}
