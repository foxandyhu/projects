package com.bfly.industry.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource("classpath:system.properties")
public class PropertyConfig {
	
	private Logger logger=LoggerFactory.getLogger(PropertyConfig.class);

	@Value("${api.server}")
	private String host;
	
	@Value("${task.poolsize}")
	private int taskPoolSize;
	
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
	
	public int getTaskPoolSize() {
		return taskPoolSize;
	}

	public void setTaskPoolSize(int taskPoolSize) {
		this.taskPoolSize = taskPoolSize;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	@PostConstruct
	public void init()
	{
		logger.info("the system property config is initialized");
	}
}
