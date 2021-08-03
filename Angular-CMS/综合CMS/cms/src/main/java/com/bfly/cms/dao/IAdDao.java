package com.bfly.cms.dao;

import com.bfly.cms.entity.Ad;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/11 17:10
 */
public interface IAdDao extends JpaRepositoryImplementation<Ad, Integer> {

    /**
     * 禁用过期的广告
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/10 0:01
     */
    @Modifying
    @Query("update Ad set status=:status where endTime<:date and enabled=true")
    void disabledExpiredAds(@Param("date") Date date, @Param("status") int status);

    /**
     * 启用展示日期开始的广告
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/25 14:19
     */
    @Modifying
    @Query("update Ad set status=:status where startTime<:date and endTime>:date and enabled=true")
    void enabledUnExpireAds(@Param("date") Date date, @Param("status") int status);
}
