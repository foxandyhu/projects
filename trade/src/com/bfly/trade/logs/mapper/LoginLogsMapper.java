package com.bfly.trade.logs.mapper;

import org.apache.ibatis.annotations.Param;

import com.bfly.trade.base.mapper.BaseMapper;
import com.bfly.trade.logs.entity.LoginLogs;

public interface LoginLogsMapper extends BaseMapper<LoginLogs> {

	/**
	 * 查找指定用户最近一次登录日志
	 * @author 胡礼波-Andy
	 * @2016年1月24日下午3:28:28
	 * @param userName
	 * @return
	 */
	public LoginLogs getLatestLog(@Param("userName") String userName,@Param("index")int index);
}
