package com.lw.iot.pbj.users.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lw.iot.pbj.core.base.persistence.BaseMapper;
import com.lw.iot.pbj.users.entity.Users;


/**
 * 用户持久层接口
 * @author 胡礼波-Andy
 * @2014年11月10日上午10:39:31
 *
 */
public interface UserMapper extends BaseMapper<Users> {

	/**
	 * 根据用户名查找用户
	 * @author 胡礼波
	 * 2012-5-18 下午07:33:24
	 * @param userName
	 * @return
	 */
	public Users getUserByName(String userName);
	
	/**
	 * 查找属于指定角色的用户
	 * @author 胡礼波-Andy
	 * @2016年9月26日下午6:57:37
	 * @param roleId
	 * @return
	 */
	public List<Users> getUsersByRole(@Param("roleId") int roleId);
	
	/**
	 * 查找未分配角色的用户
	 * @author andy_hulibo@163.com
	 * @2017年10月31日 下午2:45:10
	 * @param params
	 * @return
	 */
	public List<Users> getUsersForUnassignRole(Map<String,Object> params);
	
	/**
	 * 查找未分配角色的用户数量
	 * @author 胡礼波-Andy
	 * @2016年9月27日下午3:05:56
	 * @return
	 */
	public int getCountUsersForUnassignRole();

}
