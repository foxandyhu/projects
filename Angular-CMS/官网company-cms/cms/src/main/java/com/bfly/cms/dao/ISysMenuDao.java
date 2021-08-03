package com.bfly.cms.dao;

import com.bfly.cms.entity.SysMenu;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

/**
 * @author andy_hulibo@163.com
 * @date 2019/7/12 18:38
 */
public interface ISysMenuDao extends JpaRepositoryImplementation<SysMenu, Integer> {
}
