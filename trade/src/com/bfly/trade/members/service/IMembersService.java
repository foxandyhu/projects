package com.bfly.trade.members.service;

import com.bfly.trade.base.service.IBaseService;
import com.bfly.trade.members.entity.Members;

public interface IMembersService extends IBaseService<Members>{
	
	/**
	 * 修改帐号状态
	 * @author 胡礼波-Andy
	 * @2015年5月10日下午6:25:47
	 * @param memberId
	 * @param enable
	 * @return
	 */
	public boolean updateMemberStatus(int memberId,boolean enable);
}
