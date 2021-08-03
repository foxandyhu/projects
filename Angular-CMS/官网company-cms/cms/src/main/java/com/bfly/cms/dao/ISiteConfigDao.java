package com.bfly.cms.dao;

import com.bfly.cms.entity.SiteConfig;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/18 11:04
 */
public interface ISiteConfigDao extends JpaRepositoryImplementation<SiteConfig, Integer> {
}
