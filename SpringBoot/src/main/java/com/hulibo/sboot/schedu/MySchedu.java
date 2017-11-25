package com.hulibo.sboot.schedu;

import java.util.Date;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时器
 * @author andy
 *
 */
@Configuration
public class MySchedu {

	 @Scheduled(fixedRate=2000)
	 public void testTasks() 
	 {      
		 System.out.println(new Date()+"定时器开始执行1"+ Thread.currentThread().getName());
	 }
	 
	 @Scheduled(fixedRate=3000)
	 public void testTasks2()
	 {
		 System.out.println(new Date()+"定时器开始执行2"+ Thread.currentThread().getName());
	 }
}
