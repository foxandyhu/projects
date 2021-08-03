package com.bfly.cms.dao;

import com.bfly.cms.entity.RegistConfig;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/20 10:43
 */
public interface IMemberRegistConfigDao extends JpaRepositoryImplementation<RegistConfig, Integer> {
}
