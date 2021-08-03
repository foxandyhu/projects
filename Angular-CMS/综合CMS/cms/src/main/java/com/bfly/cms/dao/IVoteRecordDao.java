package com.bfly.cms.dao;

import com.bfly.cms.entity.VoteRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * 投票记录
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/11 22:24
 */
public interface IVoteRecordDao extends JpaRepositoryImplementation<VoteRecord, Integer> {

    /**
     * 获得投票次数
     *
     * @param memberId
     * @param voteTopicId
     * @param date
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/11 22:37
     */
    @Query(value = "select count(1) from vote_record where member_id=:memberId and vote_topic_id=:voteTopicId and date_format(vote_time, '%Y-%m-%d %H')=date_format(:date, '%Y-%m-%d %H')", nativeQuery = true)
    long getRecordCount(@Param("memberId") int memberId, @Param("voteTopicId") int voteTopicId, @Param("date") Date date);

    /**
     * 获得投票次数
     *
     * @param cookie
     * @param voteTopicId
     * @param date
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/11 22:48
     */
    @Query(value = "select count(1) from vote_record where vote_cookie=:cookie and vote_topic_id=:voteTopicId and date_format(vote_time, '%Y-%m-%d %H')=date_format(:date, '%Y-%m-%d %H')", nativeQuery = true)
    long getRecordCount(@Param("cookie") String cookie, @Param("voteTopicId") int voteTopicId, @Param("date") Date date);
}
