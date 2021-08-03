package com.bfly.cms.dao;

import com.bfly.cms.entity.SysTask;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/14 11:58
 */
public interface ISysTaskDao extends JpaRepositoryImplementation<SysTask, Integer> {

    /**
     * 根据任务名称查找任务
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/27 19:27
     */
    @Query("from SysTask where name=:name")
    SysTask getTaskByName(String name);
}
