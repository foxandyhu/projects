package com.bfly.cms.dao;

import com.bfly.cms.entity.JobApply;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/12 14:33
 */
public interface IJobApplyDao extends JpaRepositoryImplementation<JobApply, Integer> {
}
