package com.bfly.cms.dao;

import com.bfly.cms.entity.MemberExt;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

/**
 * 会员数据接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/7 17:21
 */
public interface IMemberExtDao extends JpaRepositoryImplementation<MemberExt, Integer> {
}
