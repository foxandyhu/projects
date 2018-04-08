package com.bfly.industry.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * Spring配置
 * @author andy_hulibo@163.com
 * 2018年1月8日下午2:33:57
 */
@Configuration
@Import({PropertyConfig.class,ServiceBeanFactory.class})
@ComponentScan(basePackages="com.bfly.industry",useDefaultFilters=false,
excludeFilters=@Filter(type=FilterType.ANNOTATION,value=Controller.class),
includeFilters=@Filter(type=FilterType.ANNOTATION,value=Service.class))
@PropertySource(ignoreResourceNotFound=true,value="classpath:system.properties")
@EnableScheduling
public class SpringConfig implements SchedulingConfigurer{
	
	private Logger logger=LoggerFactory.getLogger(SpringConfig.class);
	
	@Autowired
	private PropertyConfig config;
	
	@Bean(name="workTaskExecutor",destroyMethod="destroy")
	public ThreadPoolTaskExecutor workTaskExecutor()
	{
		ThreadPoolTaskExecutor logTaskExecutor=new ThreadPoolTaskExecutor();
		logTaskExecutor.setCorePoolSize(1);
		logTaskExecutor.setKeepAliveSeconds(200);
		logTaskExecutor.setMaxPoolSize(20);
		logTaskExecutor.setQueueCapacity(100);
		return logTaskExecutor;
	}

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setScheduler(taskScheduler());
	}
	
    @Bean(destroyMethod="shutdown")
    public Executor taskScheduler() {
        return Executors.newScheduledThreadPool(config.getTaskPoolSize());
    }
    
	@PostConstruct
	public void init()
	{
		logger.info("the spring root config is initialized");
	}
}
