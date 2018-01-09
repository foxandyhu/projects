package com.lw.iot.pbj.logs.service.impl;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lw.iot.pbj.core.annotation.ActionModel;
import com.lw.iot.pbj.core.base.service.impl.BaseServiceImpl;
import com.lw.iot.pbj.logs.entity.SysLog;
import com.lw.iot.pbj.logs.service.ILogService;
import com.lw.iot.pbj.users.entity.Users;

/**
 * 系统日志业务接口
 * @author 胡礼波  andy_hulibo@163.com
 * @2017年10月30日 下午4:02:26
 */
@Service("LogServiceImpl")
@Transactional(propagation=Propagation.SUPPORTS,rollbackFor=Exception.class)
@ActionModel(description="日志操作")
public class LogServiceImpl extends BaseServiceImpl<SysLog> implements ILogService {

	private static Logger logger=Logger.getLogger(LogServiceImpl.class);

	@Override	
	public int operating(Users user,String ip,String modelName,String methodName,String logInfo )
	{
		SysLog log=new SysLog();
		log.setLogInfo(logInfo);
		log.setMethodName(methodName);
		log.setModelName(modelName);
		try
		{
			if(user!=null)
			{
				log.setUserId(user.getId());
				log.setUserName(user.getUserName());
			}
		}catch (Exception e) {
			logger.warn("日志操作出错:"+e);
		}
		log.setLogTime(new Date());
		log.setIp(ip);
		return save(log);
	}
}
