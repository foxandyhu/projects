package com.bfly.cms.dao;

import com.bfly.cms.entity.Company;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/18 11:04
 */
public interface ICompanyDao extends JpaRepositoryImplementation<Company, Integer> {
}
