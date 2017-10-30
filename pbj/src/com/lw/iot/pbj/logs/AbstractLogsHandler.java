package com.lw.iot.pbj.logs;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;

import com.lw.iot.pbj.common.constant.SysConst;
import com.lw.iot.pbj.common.util.ContextUtil;
import com.lw.iot.pbj.common.util.DataConvertUtils;
import com.lw.iot.pbj.common.util.ThreadLocalUtil;
import com.lw.iot.pbj.common.util.reflect.ReflectUtils;
import com.lw.iot.pbj.core.annotation.RecordType;
import com.lw.iot.pbj.logs.service.ILogService;
import com.lw.iot.pbj.users.entity.Users;

public abstract class AbstractLogsHandler {

	@Autowired
	private ILogService logService;

	@Autowired @Qualifier("logTaskExecutor")
	private TaskExecutor logTaskExecutor;
	private static Logger logger = Logger.getLogger(LogsExceptionHandler.class);
	
	/**
	 * 方法异常日志处理
	 * @author 胡礼波
	 * 2012-6-9 下午02:40:55
	 * @param join
	 */
	public void doAfterThrowing(final JoinPoint join,final String message) {
		final Class<?> c=join.getTarget().getClass();
		@SuppressWarnings("unchecked")
		Map<String,Object> map=ThreadLocalUtil.get(Map.class);
		final String ip=getIp(map);
		final Users user=getUser(map);
		logTaskExecutor.execute(new Runnable()
		{
			public void run()
			{
				try {
							RecordType recordType=ReflectUtils.getLogRecord(c);
							if(recordType==RecordType.IGNORE)
							{
								return;
							}	
							String methodName=join.getSignature().getName();
							String classModelDes=ReflectUtils.getModelDescription(c);
							Class<?>[] types=((CodeSignature)join.getStaticPart().getSignature()).getParameterTypes();
							
							Method method=c.getMethod(methodName,types);
							recordType=ReflectUtils.getLogRecord(method);
							if(recordType==RecordType.IGNORE)
							{
								return;
							}
							
							String methodModelDes=ReflectUtils.getModelDescription(method);
							logService.operating(user,ip, classModelDes, methodModelDes,message);
				}
				catch (Exception e) {
					logger.error(message+e);
				}
			}
		});
		
	}
	
	/**
	 * 获得用户对象
	 * @author 胡礼波
	 * 2014-9-22 下午2:06:25
	 * @return
	 */
	private Users getUser(Map<String,Object> map)
	{
		if(map!=null)
		{
			Users user=new Users();
			if(map.containsKey(SysConst.USERID))
			{
				user.setId(DataConvertUtils.convertToInteger(String.valueOf(map.get(SysConst.USERID))));
				return user;
			}
		}
		return ContextUtil.getContextLoginUser();
	}
	
	/**
	 * 客户端请求IP地址
	 * @author 胡礼波
	 * 2014-9-22 下午1:59:19
	 * @return
	 */
	private String getIp(Map<String,Object> map)
	{
		if(map!=null)
		{
			if(map.containsKey(SysConst.IP))
			{
				return String.valueOf(map.get(SysConst.IP));
			}
		}
		final HttpServletRequest request=ContextUtil.getHttpServletRequest();
		return ContextUtil.getClientIp(request);
	}
}
