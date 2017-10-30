package com.lw.iot.pbj.logs.persistence;

import org.apache.ibatis.annotations.Param;

import com.lw.iot.pbj.core.base.persistence.BaseMapper;
import com.lw.iot.pbj.logs.entity.LoginLogs;

/**
 * 登录日志数据接口
 * @author 胡礼波  andy_hulibo@163.com
 * @2017年10月30日 下午4:02:15
 */
public interface LoginLogsMapper extends BaseMapper<LoginLogs> {

	/**
	 * 查找指定用户最近一次登录日志
	 * @author 胡礼波-Andy
	 * @2016年1月24日下午3:28:28
	 * @param userName 用户名
	 * @param index 索引
	 * @return
	 */
	public LoginLogs getLatestLog(@Param("userName") String userName,@Param("index")int index);
}
