package com.bfly.cms.dao;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import com.bfly.cms.entity.AdSpace;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/11 17:10
 */
public interface IAdSpaceDao extends JpaRepositoryImplementation<AdSpace, Integer> {
}
