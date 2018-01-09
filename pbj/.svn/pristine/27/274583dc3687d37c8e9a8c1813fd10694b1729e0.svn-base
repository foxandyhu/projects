package com.lw.iot.pbj.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志记录注解
 * @author 胡礼波-Andy
 * @2015年6月8日上午10:59:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.TYPE,ElementType.METHOD})
public @interface LogRecord {

	RecordType record() default RecordType.REQUIRED;
}
