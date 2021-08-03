package com.bfly.cms.dao;

import com.bfly.cms.entity.SensitiveWords;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/17 14:47
 */
public interface ISensitiveWordsDao extends JpaRepositoryImplementation<SensitiveWords, Integer> {
}
