package com.bfly.cms.dao;

import com.bfly.cms.entity.SiteAccessPage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * @author andy_hulibo@163.com
 * @date 2019/7/23 18:05
 */
public interface ISiteAccessPageDao extends JpaRepositoryImplementation<SiteAccessPage, Integer> {

    /**
     * 根据SessionID,seq 查找页面记录
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/23 18:06
     */
    @Query("select access from SiteAccessPage as access where access.sessionId=:sessionId and access.seq=:seq")
    SiteAccessPage findSiteAccessPageBySessionIdAndSeq(@Param("sessionId") String sessionId, @Param("seq") long seq);

    /**
     * 清空指定日期的页面访问记录
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/24 10:02
     */
    @Modifying
    @Query("delete from SiteAccessPage where accessDate=:date")
    int removeSiteAccessPageByAccessDate(@Param("date") Date date);
}
