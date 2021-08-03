package com.bfly.cms.service.impl;

import com.bfly.cms.entity.AdSpace;
import com.bfly.cms.service.IAdSpaceService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/11 17:09
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class AdSpaceServiceImpl extends BaseServiceImpl<AdSpace, Integer> implements IAdSpaceService {
}
