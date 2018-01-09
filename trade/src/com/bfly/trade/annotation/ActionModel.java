package com.bfly.trade.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 该注解用于描述类和方法的作用
 * @author andy_hulibo@163.com
 * 2018年1月8日下午3:18:43
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE,ElementType.METHOD})
public @interface ActionModel {
	String value() default "";
}
