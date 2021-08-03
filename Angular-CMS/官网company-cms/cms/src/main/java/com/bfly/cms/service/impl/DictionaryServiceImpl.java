package com.bfly.cms.service.impl;

import com.bfly.cms.dao.IDictionaryDao;
import com.bfly.cms.entity.Dictionary;
import com.bfly.cms.service.IDictionaryService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/14 11:11
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class DictionaryServiceImpl extends BaseServiceImpl<Dictionary, Integer> implements IDictionaryService {

    @Autowired
    private IDictionaryDao dictionaryDao;

    @Override
    public List<Dictionary> getList(Map<String, Object> exactQueryProperty) {
        Map<String, Sort.Direction> sortMap = new HashMap<>(1);
        sortMap.put("seq", Sort.Direction.ASC);
        return super.getList(exactQueryProperty, null, sortMap);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Dictionary dictionary) {
        boolean exist = checkExist(dictionary);
        Assert.isTrue(!exist, "该数据字典数据已重复!");
        return super.save(dictionary);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(Dictionary dictionary) {
        boolean exist = checkExist(dictionary);
        Assert.isTrue(!exist, "该数据字典数据已重复!");
        return super.edit(dictionary);
    }

    /**
     * 检查是否存在相同的数据字典
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/14 11:34
     */
    private boolean checkExist(Dictionary dictionary) {
        Dictionary dic = get(new HashMap<String, Object>(3) {
            private static final long serialVersionUID = 6340025449740664103L;

            {
                put("name", dictionary.getName().trim());
                put("value", dictionary.getValue());
                put("type", dictionary.getType().trim());
            }
        });
        return dic != null && (dic.getId() != dictionary.getId());
    }

    @Override
    public List<String> getTypes() {
        String key = "dictionary_type_list";
        Cache cache = getCache();
        List<String> types = cache.get(key, List.class);
        if (types == null) {
            types = dictionaryDao.getTypes();
            if (CollectionUtils.isNotEmpty(types)) {
                cache.put(key, types);
            }
        }
        return types;
    }

    @Override
    public Dictionary getDictionary(String type, String value) {
        String key = type.concat("_") + value;
        Cache cache = getCache();
        Dictionary dictionary = cache.get(key, Dictionary.class);
        if (dictionary == null) {
            dictionary = dictionaryDao.getDictionary(type, value);
            if (dictionary != null) {
                cache.put(key, dictionary);
            }
        }
        return dictionary;
    }
}
