package com.bfly.trade.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource(ignoreResourceNotFound=true,value= "classpath:system.properties")
public class PropertyConfig {
	
	private Logger logger=LoggerFactory.getLogger(PropertyConfig.class);

	@Value("${jdbc.driver}")
	private String driver;
	
	@Value("${jdbc.url}")
	private String url;
	
	@Value("${jdbc.user}")
	private String user;
	
	@Value("${jdbc.password}")
	private String password;
	
	@Value("${jdbc.maximumConnectionCount}")
	private int maximumConnectionCount;
	
	@Value("${jdbc.minimumConnectionCount}")
	private int minimumConnectionCount;
	
	@Value("${jdbc.houseKeepingTestSql}")
	private String houseKeepingTestSql;
	
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
	
	
	
	public String getDriver() {
		return driver;
	}



	public void setDriver(String driver) {
		this.driver = driver;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public String getUser() {
		return user;
	}



	public void setUser(String user) {
		this.user = user;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public int getMaximumConnectionCount() {
		return maximumConnectionCount;
	}



	public void setMaximumConnectionCount(int maximumConnectionCount) {
		this.maximumConnectionCount = maximumConnectionCount;
	}



	public int getMinimumConnectionCount() {
		return minimumConnectionCount;
	}



	public void setMinimumConnectionCount(int minimumConnectionCount) {
		this.minimumConnectionCount = minimumConnectionCount;
	}



	public String getHouseKeepingTestSql() {
		return houseKeepingTestSql;
	}



	public void setHouseKeepingTestSql(String houseKeepingTestSql) {
		this.houseKeepingTestSql = houseKeepingTestSql;
	}



	public int getTaskPoolSize() {
		return taskPoolSize;
	}



	public void setTaskPoolSize(int taskPoolSize) {
		this.taskPoolSize = taskPoolSize;
	}



	@PostConstruct
	public void init()
	{
		logger.info("the system property config is initialized");
	}
}
