package com.hulibo.sboot.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * AOP
 * @author andy
 *
 */
@Aspect
@Component
public class MyAop {

	@Pointcut("execution(* com.hulibo.sboot..service..*.*(..))")
	@Order(1)
	public void pointcut() {}
	
	@Before("pointcut()")
	public void doBefore(JoinPoint joinPoint)
	{
		System.out.println("切入之前AOP "+joinPoint.getSignature().getName());
	}
	
	@After("pointcut()")
	public void doAfter()
	{
		System.out.println("切入点后AOP ");
	}
	
	@AfterThrowing("pointcut()")
	public void doThrowing()
	{
		System.out.println("发生异常AOP");
	}
	
	//方法上有该注解的都会匹配
	@Pointcut("@annotation(com.hulibo.sboot.annotation.Login)")
	@Order(2)
	public void annotationPointcut() {}
	
	@Before("annotationPointcut()")
	public void doAnnotationBefore(JoinPoint joinPoint)
	{
		System.out.println("Annotation切入之前AOP"+joinPoint.getSignature().getName());
	}
	
	@After("annotationPointcut()")
	public void doAnnotationAfter()
	{
		System.out.println("Annotation切入点后AOP");
	}
}
