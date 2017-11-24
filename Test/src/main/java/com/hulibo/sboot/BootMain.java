package com.hulibo.sboot;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.hulibo.sboot.entity.User;

public class BootMain {

	public static void main(String[] args) {
		AnnotationConfigApplicationContext context=new AnnotationConfigApplicationContext(SpringConfig.class);
		UserService service=context.getBean(UserService.class);
		List<User> list=service.getList();
		for (User user : list) {
			System.out.println(String.format("%s %s",user.getName(),user.getPassword()));
		}
		context.close();
	}
}
