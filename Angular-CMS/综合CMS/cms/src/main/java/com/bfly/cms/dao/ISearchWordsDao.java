package com.bfly.cms.dao;

import com.bfly.cms.entity.SearchWords;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/17 14:47
 */
public interface ISearchWordsDao extends JpaRepositoryImplementation<SearchWords, Integer> {
}
