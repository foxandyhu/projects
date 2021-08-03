package com.bfly.cms.dao;

import com.bfly.cms.entity.SmsProvider;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/18 11:31
 */
public interface ISmsProviderDao extends JpaRepositoryImplementation<SmsProvider, Integer> {
}
