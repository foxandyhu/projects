package com.bfly.cms.dao;

import com.bfly.cms.entity.MemberGroup;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;

/**
 * 会员数据接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/7 17:21
 */
public interface IMemberGroupDao extends JpaRepositoryImplementation<MemberGroup, Integer> {

    /**
     * 更新会员的组Id
     * 把所属sourceGroupId 的会员组ID更新为targetGroupId
     *
     * @param sourceGroupId 原组ID
     * @param targetGroupId 要修改的组ID
     * @return 受影响行数
     * @author andy_hulibo@163.com
     * @date 2019/7/19 15:08
     */
    @Modifying
    @Query(value = "UPDATE member set group_id=:targetGroupId where group_id=:sourceGroupId", nativeQuery = true)
    int editMembersGroup(@Param("sourceGroupId") int sourceGroupId, @Param("targetGroupId") int targetGroupId);

    /**
     * 获得默认的会员组
     *
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/6 10:43
     */
    @Query("select group from MemberGroup as group where defaults=true")
    MemberGroup getDefaultMemberGroup();

    /**
     * 清空所有默认会员组
     *
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/6 10:46
     */
    @Modifying
    @Query("update MemberGroup set defaults=false where defaults=true")
    int clearDefaultMemberGroup();
}
