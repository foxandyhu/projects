package com.bfly.trade.logs.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bfly.trade.annotation.ActionModel;
import com.bfly.trade.base.service.impl.BaseServiceImpl;
import com.bfly.trade.logs.entity.LoginLogs;
import com.bfly.trade.logs.mapper.LoginLogsMapper;
import com.bfly.trade.logs.service.ILoginLogsService;
import com.bfly.trade.members.entity.Members;
import com.bfly.trade.members.service.IMembersService;
import com.bfly.trade.users.entity.Users;
import com.bfly.trade.users.service.IUsersService;

@Service("LoginLogsServiceImpl")
@Transactional(propagation=Propagation.SUPPORTS)
@ActionModel("登录日志管理")
public class LoginLogsServiceImpl extends BaseServiceImpl<LoginLogs> implements ILoginLogsService {

	@Autowired
	private LoginLogsMapper loginLogsMapper;
	@Autowired
	private IMembersService memberService;
	@Autowired
	private IUsersService userService;

	@Override
	public LoginLogs getLatestLog(int memberId,int index,int type)
	{
		Assert.isTrue(index>0,"记录数必须大于0");
		index=index-1;
		String userName=null;
		if(LoginLogs.MEMBER_LOGIN==type)
		{
			Members member=memberService.get(memberId);
			userName=member==null?null:member.getCusCode();
		}else if(LoginLogs.USERS_LOGIN==type)
		{
			Users user=userService.get(memberId);
			userName=user==null?null:user.getUserName();
		}
		if(userName!=null)
		{
			return loginLogsMapper.getLatestLog(userName,index);
		}
		return null;
	}

}
