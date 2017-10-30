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


@Service("LogServiceImpl")
@Transactional(propagation=Propagation.SUPPORTS)
@ActionModel(description="日志操作")
public class LogServiceImpl extends BaseServiceImpl<SysLog> implements ILogService {

	private static Logger logger=Logger.getLogger(LogServiceImpl.class);

	
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
