package com.lw.iot.pbj.logs.service;


import com.lw.iot.pbj.core.base.service.IBaseService;
import com.lw.iot.pbj.logs.entity.SysLog;
import com.lw.iot.pbj.users.entity.Users;


/**
 * 日志记录接口
 * @author 胡礼波
 * 2012-5-23 下午11:27:57
 */
public interface ILogService extends IBaseService<SysLog>{
	
	
	/**
	 * 日志操作
	 * @author 胡礼波
	 * 2012-5-23 下午11:29:31
	 * @param user 登录用户对象
	 * @param ip IP地址
	 * @param modelName 模块名称 
	 * @param methodName 方法名称
	 * @param logInfo 其他信息
	 * @return
	 */
	public int operating(Users user,String ip,String modelName,String methodName,String logInfo );	

	/**
	 * 保存日志
	 * @author 胡礼波
	 * 2012-5-23 下午11:29:59
	 * @param log 日志对象
	 * @return
	 */
	public int save(SysLog log);
}
