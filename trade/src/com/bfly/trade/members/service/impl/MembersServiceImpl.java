package com.bfly.trade.members.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.trade.annotation.ActionModel;
import com.bfly.trade.base.service.impl.BaseServiceImpl;
import com.bfly.trade.members.entity.Members;
import com.bfly.trade.members.mapper.MembersMapper;
import com.bfly.trade.members.service.IMembersService;

@Service("MemberServiceImpl")
@Transactional(propagation=Propagation.SUPPORTS)
@ActionModel("会员管理")
public class MembersServiceImpl extends BaseServiceImpl<Members> implements IMembersService {
	
	private Logger logger=Logger.getLogger(MembersServiceImpl.class);
	
	@Autowired
    private MembersMapper memberMapper;
	
}
