package com.bfly.cms.dao;

import com.bfly.cms.entity.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/11 17:10
 */
public interface ICityDao extends JpaRepositoryImplementation<City, Integer> {

    /**
     * 根据拼音查找城市
     *
     * @author andy_hulibo@163.com
     * @date 2020/8/26 11:21
     */
    @Query("select c from City as c where c.level=:level and c.pinyin=:pinyin")
    List<City> getCity(@Param("pinyin") String pinyin, @Param("level") int level);
}
