package com.hulibo.sboot.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hulibo.sboot.entity.User;
import com.hulibo.sboot.service.UserService;

@RestController
public class MyController {

	@Autowired
	private UserService service;
	
	@RequestMapping("/")
    String home(HttpServletRequest request) {
		List<User> list=service.getList();
		for (User user : list) {
			System.out.println(user.getName());
		}
		service.annotationtest();
        return "你好世界!";
    }
	
	@RequestMapping(value="/add")
	public String add()
	{
		service.save();
		return "添加成功";
	}
}
