package com.bfly.industry.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.JstlView;

/**
 * SpringMVC配置
 * @author andy_hulibo@163.com
 * 2018年1月8日下午2:33:57
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages="com.bfly.industry",useDefaultFilters=false,includeFilters=@Filter(type=FilterType.ANNOTATION,value=Controller.class))
public class SpringMvcConfig extends WebMvcConfigurerAdapter{

	private Logger logger=LoggerFactory.getLogger(SpringMvcConfig.class);
	
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		registry.jsp().prefix("/").suffix(".jsp").viewClass(JstlView.class);
	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@PostConstruct
	public void init()
	{
		logger.info("the spring mvc config is initialized");
	}
}
