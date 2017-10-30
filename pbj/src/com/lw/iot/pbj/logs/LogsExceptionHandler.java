package com.lw.iot.pbj.logs;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;

/**
 * AOP异常日志类
 * @author 胡礼波 2012-6-9 下午01:52:51
 */
public class LogsExceptionHandler extends AbstractLogsHandler{

	static private List<Class<?>> listC=null;
	static
	{
		listC=new ArrayList<Class<?>>();
	}

	/**
	 * 方法异常日志处理
	 * @author 胡礼波
	 * 2012-6-9 下午02:40:55
	 * @param join
	 */
	public void doAfterThrowing(final JoinPoint join) {
		final Class<?> c=join.getTarget().getClass();
		//过滤不需要日志处理的类
		 if(listC.contains(c))
		 {
			 return;
		 }
		 super.doAfterThrowing(join,"异常日志操作成功!");
	}
}
