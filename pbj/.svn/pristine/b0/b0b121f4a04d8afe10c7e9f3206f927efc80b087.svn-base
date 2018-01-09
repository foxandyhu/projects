package com.lw.iot.pbj.logs;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;

/**
 * AOP系统日志记录类
 * @author 胡礼波
 * 2012-5-24 下午04:08:25
 */
public class LogsHandler extends AbstractLogsHandler{

	static private List<Class<?>> listC=null;
	static
	{
		listC=new ArrayList<Class<?>>();
	}
	
	public void addLog(final JoinPoint join) 
	{
		final Class<?> c=join.getTarget().getClass();
		//过滤不需要日志处理的类
		 if(listC.contains(c))
		 {
			 return;
		 }
		 super.doAfterThrowing(join,"日志操作成功!");
	}
}
