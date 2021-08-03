package com.bfly.cms.dao;

import com.bfly.cms.entity.Dictionary;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/14 11:12
 */
public interface IDictionaryDao extends JpaRepositoryImplementation<Dictionary, Integer> {

    /**
     * 获得数据字典的所有类型
     *
     * @return 类型集合
     * @author andy_hulibo@163.com
     * @date 2018/12/14 11:14
     */
    @Query("select d.type from Dictionary as d group by d.type")
    List<String> getTypes();

    /**
     * 根据类型和值查找数据字典
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/9 10:08
     */
    @Query("select d from Dictionary as d where type=:type and value=:value")
    Dictionary getDictionary(@Param("type") String type, @Param("value") String value);
}
