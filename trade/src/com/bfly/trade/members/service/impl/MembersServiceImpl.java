package com.bfly.trade.members.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bfly.trade.annotation.ActionModel;
import com.bfly.trade.base.service.impl.BaseServiceImpl;
import com.bfly.trade.members.entity.Members;
import com.bfly.trade.members.mapper.MembersMapper;
import com.bfly.trade.members.service.IMembersService;

@Service("MemberServiceImpl")
@Transactional(propagation=Propagation.SUPPORTS)
@ActionModel("会员管理")
public class MembersServiceImpl extends BaseServiceImpl<Members> implements IMembersService {
	
	@Autowired
    private MembersMapper memberMapper;
	
	@Override
	@Transactional
	@ActionModel("更新帐号状态")
	public boolean updateMemberStatus(int memberId, boolean enable) {
		Members member=memberMapper.getById(memberId);
		Assert.notNull(member,"不存在该会员!");
		member.setEnable(enable);
		return memberMapper.editById(member)>0;
	}
}
