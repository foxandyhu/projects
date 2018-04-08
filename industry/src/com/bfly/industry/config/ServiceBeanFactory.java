package com.bfly.industry.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bfly.industry.members.service.MembersService;
import com.bfly.industry.members.service.SellerInfoService;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;

@Configuration
public class ServiceBeanFactory {

	private Logger logger=LoggerFactory.getLogger(ServiceBeanFactory.class);
	
	@Autowired
	private PropertyConfig config;
	
	/**
	 * 会员服务接口
	 * @author andy_hulibo@163.com
	 * @2018年3月30日 上午11:13:37
	 * @return
	 */
	@Bean(name="membersService")
	public MembersService membersService()
	{
		return Factory.builder(MembersService.class,config.getHost());
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
		return Factory.builder(SellerInfoService.class,config.getHost());
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
	
	@PostConstruct
	public void init()
	{
		logger.info("the service BeanFactory is initialized");
	}
}
