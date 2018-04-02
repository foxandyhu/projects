package com.bfly.trade.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;

import com.bfly.trade.interceptor.ApiInterceptor;
import com.bfly.trade.interceptor.LoginInterceptor;

/**
 * SpringMVC配置
 * @author andy_hulibo@163.com
 * 2018年1月8日下午2:33:57
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.bfly.trade",useDefaultFilters=false,includeFilters={@Filter(type=FilterType.ANNOTATION,value=Controller.class),@Filter(type=FilterType.ANNOTATION,value=ControllerAdvice.class)})
public class SpringMvcConfig extends WebMvcConfigurerAdapter{

	@Autowired
	private ApiInterceptor apiInterceptor;
	@Autowired
	private LoginInterceptor loginInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(apiInterceptor).addPathPatterns("/api/**");
		registry.addInterceptor(loginInterceptor).addPathPatterns("/**").excludePathPatterns("/api/**");
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp().prefix("/").suffix(".jsp").viewClass(JstlView.class);
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Bean(name="apiInterceptor")
	public ApiInterceptor apiInterceptor()
	{
		ApiInterceptor apiInterceptor=new ApiInterceptor();
		return apiInterceptor;
	}
	
	@Bean(name="loginInterceptor")
	public LoginInterceptor loginInterceptor()
	{
		LoginInterceptor loginInterceptor=new LoginInterceptor();
		return loginInterceptor;
	}
}
