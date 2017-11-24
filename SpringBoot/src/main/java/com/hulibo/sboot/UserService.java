package com.hulibo.sboot;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hulibo.sboot.entity.User;

@Service
@Transactional(propagation=Propagation.SUPPORTS)
public class UserService {

	@Autowired
	private UserMapper userDao;
	
	public List<User> getList()
	{
		return userDao.getList();
	}
	
	@Transactional
	public void save()
	{
		User user=new User();
		user.setName(Calendar.getInstance().getTime().toString());
		user.setPassword(new Random().nextBoolean()+"");
		userDao.save(user);
	}
}
