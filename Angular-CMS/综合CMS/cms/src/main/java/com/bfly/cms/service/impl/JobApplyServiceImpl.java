package com.bfly.cms.service.impl;

import com.bfly.cms.entity.JobApply;
import com.bfly.cms.service.IJobApplyService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/12 14:32
 */
@Service
@Transactional(propagation= Propagation.SUPPORTS, rollbackFor = Exception.class)
public class JobApplyServiceImpl extends BaseServiceImpl<JobApply, Integer> implements IJobApplyService {
}
