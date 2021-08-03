package com.bfly.cms.dao;

import com.bfly.cms.entity.SiteAccessStatisticHour;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author andy_hulibo@163.com
 * @date 2019/7/24 9:55
 */
public interface ISiteAccessStatisticHourDao extends JpaRepositoryImplementation<SiteAccessStatisticHour, Integer> {

    /**
     * 根据指定时间统计小时段数据
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/24 13:00
     */
    @Query(value = "select sum(hour_ip) as ip,sum(hour_uv) as uv,sum(hour_pv) as pv,CONCAT(access_hour,':00:-',access_hour,':59') as time, ANY_VALUE(access_date) as value from site_access_statistic_hour where DATE_FORMAT(access_date,'%Y-%m-%d')=DATE_FORMAT(:date,'%Y-%m-%d') GROUP BY access_hour", nativeQuery = true)
    List<Map<String, Object>> getSiteAccessStatisticHourByDate(Date date);
}
