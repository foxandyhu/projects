package com.bfly.cms.dao;

import com.bfly.cms.entity.SysLog;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/10 16:36
 */
public interface ISysLogDao extends JpaRepositoryImplementation<SysLog, Integer> {
}
