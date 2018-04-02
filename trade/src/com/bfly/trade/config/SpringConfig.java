package com.bfly.trade.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.sql.DataSource;

import org.logicalcobwebs.proxool.ProxoolDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Spring配置
 * @author andy_hulibo@163.com
 * 2018年1月8日下午2:33:57
 */
@Configuration
@ComponentScan(basePackages="com.bfly.trade",useDefaultFilters=false,
excludeFilters=@Filter(type=FilterType.ANNOTATION,value=Controller.class),
includeFilters=@Filter(type=FilterType.ANNOTATION,value=Service.class))
@PropertySource(ignoreResourceNotFound=true,value="classpath:system.properties")
@MapperScan(basePackages="com.bfly.trade.**.mapper")
@EnableTransactionManagement
@EnableScheduling()
public class SpringConfig implements SchedulingConfigurer{
	
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
	
	@Bean(name="dataSource")
	public DataSource dataSource()
	{
		ProxoolDataSource dataSource=new ProxoolDataSource();
		dataSource.setDriver(driver);
		dataSource.setDriverUrl(url);
		dataSource.setUser(user);
		dataSource.setPassword(password);
		dataSource.setMaximumConnectionCount(maximumConnectionCount);
		dataSource.setMinimumConnectionCount(minimumConnectionCount);
		dataSource.setHouseKeepingTestSql(houseKeepingTestSql);
		return dataSource;
	}
	
	@Bean(name="transactionManager")
	public DataSourceTransactionManager transactionManager(DataSource datasource)
	{
		DataSourceTransactionManager manager=new DataSourceTransactionManager();
		manager.setDataSource(datasource);
		return manager;
	}
	
	@Bean(name="sessionFactory")
	public SqlSessionFactoryBean sessionFactory(DataSource datasource)
	{
		SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(datasource);
		sessionFactory.setConfigLocation(new ClassPathResource("SqlMapConfig.xml"));
		return sessionFactory;
	}

	@Bean(name="logTaskExecutor",destroyMethod="destroy")
	public ThreadPoolTaskExecutor logTaskExecutor()
	{
		ThreadPoolTaskExecutor logTaskExecutor=new ThreadPoolTaskExecutor();
		logTaskExecutor.setCorePoolSize(1);
		logTaskExecutor.setKeepAliveSeconds(200);
		logTaskExecutor.setMaxPoolSize(2);
		logTaskExecutor.setQueueCapacity(30);
		return logTaskExecutor;
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
