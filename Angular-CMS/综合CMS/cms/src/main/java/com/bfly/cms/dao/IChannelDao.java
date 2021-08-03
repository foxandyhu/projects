package com.bfly.cms.dao;

import com.bfly.cms.entity.Channel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/19 10:32
 */
public interface IChannelDao extends JpaRepositoryImplementation<Channel, Integer> {

    /**
     * 修改栏目排序
     *
     * @param channelId 栏目ID
     * @param seq       排序序号
     * @return 受影响行数
     * @author andy_hulibo@163.com
     * @date 2019/8/4 15:24
     */
    @Modifying
    @Query("update Channel set seq=:seq where id=:channelId")
    int editChannelSeq(@Param("channelId") int channelId, @Param("seq") int seq);

    /**
     * 获得最大的排序序号
     *
     * @return 返回最大的排序序号
     * @author andy_hulibo@163.com
     * @date 2019/8/4 20:46
     */
    @Query(value = "select IFNULL(max(seq),0) as seq from channel", nativeQuery = true)
    int getMaxSeq();

    /**
     * 解除栏目关系设置parentId为0
     *
     * @param channelParentId 解除关系parentId为channelParentId的数据
     * @return 受影响行数
     * @author andy_hulibo@163.com
     * @date 2019/8/5 21:47
     */
    @Modifying
    @Query("update Channel set parentId=0 where parentId=:channelParentId")
    int editChannelParent(@Param("channelParentId") int channelParentId);

    /**
     * 根据栏目路径查找栏目
     *
     * @param path 路径名称
     * @return 栏目对象
     * @author andy_hulibo@163.com
     * @date 2019/8/19 16:37
     */
    @Query("select c from Channel as c where path=:path")
    Channel getChannelByPath(@Param("path") String path);

    /**
     * @author andy_hulibo@163.com
     * @date 2020/6/20 22:11
     */
    @Query("select c from Channel as c where alias=:alias")
    Channel getChannelByAlias(@Param("alias") String alias);

    /**
     * 删除栏目
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/20 22:03
     */
    @Modifying
    @Query(value = "delete from channel where id in (:channelIds)", nativeQuery = true)
    int deleteChannel(@Param("channelIds") Integer... channelIds);
}
