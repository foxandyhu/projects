package com.hulibo.sboot;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hulibo.sboot.entity.User;

@SpringBootApplication
@RestController
public class MyApplication{

	@Autowired
	private UserService service;
	
	@RequestMapping("/")
    String home(HttpServletRequest request) {
		List<User> list=service.getList();
		for (User user : list) {
			System.out.println(user.getName());
		}
        return "你好世界!";
    }
	
	@RequestMapping(value="/add")
	public String add()
	{
		service.save();
		return "添加成功";
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(MyApplication.class,args);
	}
}
