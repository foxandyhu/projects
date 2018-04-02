package com.bfly.industry.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import com.bfly.industry.members.service.MembersService;
import com.bfly.industry.members.service.SellerInfoService;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

@Configuration
@PropertySource("classpath:system.properties")
public class ServiceBeanFactory {

	@Value("${api.server}")
	private String host;

	/**
	 * Property文件${}占位符解析必须使用的Bean
	 * @author andy_hulibo@163.com
	 * @2018年3月30日 上午11:13:13
	 * @return
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer()
	{
      return new PropertySourcesPlaceholderConfigurer();
	}
	
	/**
	 * 会员服务接口
	 * @author andy_hulibo@163.com
	 * @2018年3月30日 上午11:13:37
	 * @return
	 */
	@Bean(name="membersService")
	public MembersService membersService()
	{
		return Factory.builder(MembersService.class,host);
	}
	
	/**
	 * 商户服务接口
	 * @author andy_hulibo@163.com
	 * @2018年3月30日 上午11:14:53
	 * @return
	 */
	@Bean(name="sellerInfoService")
	public SellerInfoService sellerInfoService()
	{
		return Factory.builder(SellerInfoService.class,host);
	}
	
	static class Factory
	{
		public static <T> T builder(Class<T> cls,String host)
		{
			T service=Feign.builder()
					.encoder(new GsonEncoder())
					.decoder(new GsonDecoder())
					.target(cls,host);
			return service;
		}
	}
}
