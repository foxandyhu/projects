package com.bfly.industry.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
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
@ComponentScan(basePackages="com.bfly.industry",useDefaultFilters=false,
excludeFilters=@Filter(type=FilterType.ANNOTATION,value=Controller.class),
includeFilters=@Filter(type=FilterType.ANNOTATION,value=Service.class))
@PropertySource(ignoreResourceNotFound=true,value="classpath:system.properties")
@EnableScheduling()
public class SpringConfig implements SchedulingConfigurer{
	
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
        return Executors.newScheduledThreadPool(taskPoolSize);
    }
}
