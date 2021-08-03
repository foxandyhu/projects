package com.bfly.cms.dao;

import com.bfly.cms.entity.EmailProvider;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/14 11:58
 */
public interface IEmailProviderDao extends JpaRepositoryImplementation<EmailProvider, Integer> {
}
