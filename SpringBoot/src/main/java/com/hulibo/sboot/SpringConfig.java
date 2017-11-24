package com.hulibo.sboot;

import java.nio.charset.Charset;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class SpringConfig extends WebMvcConfigurerAdapter{
	
	/**
	 * 全局消息转换器
	 * @author 胡礼波-Andy
	 * @2017年11月23日下午5:50:10
	 * @return
	 */
	@Bean
	public  StringHttpMessageConverter stringHttpMessageConverter()
	{
		StringHttpMessageConverter converter=new StringHttpMessageConverter(Charset.forName("UTF-8"));
		return converter;
	}
	
	/**
	 * 全局拦截器
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new MyInterceptor1()).addPathPatterns("/**");
		registry.addInterceptor(new MyInterceptor2()).addPathPatterns("/**");
	}
}
