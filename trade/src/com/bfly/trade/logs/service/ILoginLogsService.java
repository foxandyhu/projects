package com.bfly.trade.logs.service;

import com.bfly.trade.base.service.IBaseService;
import com.bfly.trade.logs.entity.LoginLogs;

/**
 * 登录日志
 * @author 胡礼波
 * 2015年12月6日上午11:32:17
 */
public interface ILoginLogsService extends IBaseService<LoginLogs> {

	/**
	 * 查询指定用户或会员最近第几次的登录情况
	 * @author 胡礼波-Andy
	 * @2016年9月6日上午10:26:57
	 * @param memberId  用户ID
	 * @param index		第几次
	 * @param type		会员用户类型
	 * @return
	 */
	public LoginLogs getLatestLog(int memberId,int index,int type);
}
