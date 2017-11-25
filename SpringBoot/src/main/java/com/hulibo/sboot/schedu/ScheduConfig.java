package com.hulibo.sboot.schedu;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * 异步多线程并行执行任务，默认多任务是串行
 * @author andy
 *
 */
@Configuration
@EnableScheduling
public class ScheduConfig implements AsyncConfigurer,SchedulingConfigurer{

	@Bean
	public ThreadPoolTaskScheduler taskScheduler()
	{
		ThreadPoolTaskScheduler pool=new ThreadPoolTaskScheduler();
		pool.setPoolSize(5);
		pool.setThreadNamePrefix("ScheduTask-");
		pool.setAwaitTerminationSeconds(60);
		pool.setWaitForTasksToCompleteOnShutdown(true);
		return pool;
	}
	
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		taskRegistrar.setTaskScheduler(this.taskScheduler());
	}

	@Override
	public Executor getAsyncExecutor() {
		return this.taskScheduler();
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return null;
	}

}
