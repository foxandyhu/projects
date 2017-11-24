package com.hulibo.sboot;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hulibo.sboot.entity.User;
@Mapper
public interface UserMapper {

	public List<User> getList();
	
	public int save(User user);
}
