package com.bfly.trade.logs;

import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 日志AOP操作
 * @author andy_hulibo@163.com
 * 2018年1月8日下午2:27:54
 */
@Aspect
@Component
public class LogsAop {

	@Pointcut("execution(* com.bfly.trade..service..*.*(..))")
	@Order(1)
	public void pointcut() {}
	
	
	/**
	 * 发生异常拦截
	 * @author andy_hulibo@163.com
	 * 2018年1月8日下午2:30:41
	 */
	@AfterThrowing("pointcut()")
	public void doThrowing()
	{
		System.out.println("doThrowing>>>>>>>>>>>>>>>>>>>>>>");
	}
	
}
