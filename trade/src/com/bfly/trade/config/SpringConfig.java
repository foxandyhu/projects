package com.bfly.trade.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.bfly.trade.interceptor.ApiInterceptor;
import com.bfly.trade.interceptor.LoginInterceptor;

/**
 * Spring配置
 * @author andy_hulibo@163.com
 * 2018年1月8日下午2:33:57
 */
@Configuration
public class SpringConfig extends WebMvcConfigurerAdapter{

	@Autowired
	private ApiInterceptor apiInterceptor;
	@Autowired
	private LoginInterceptor loginInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(apiInterceptor).addPathPatterns("/api/**");
		registry.addInterceptor(loginInterceptor).addPathPatterns("/**");
	}
}
