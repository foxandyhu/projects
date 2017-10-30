package com.lw.iot.pbj.users.service;

import java.util.List;

import com.lw.iot.pbj.core.base.service.IBaseService;
import com.lw.iot.pbj.users.entity.Users;


/**
 * 用户业务层接口
 * @author 胡礼波-Andy
 * @2014年11月10日上午10:38:36
 *
 */
public interface IUserService extends IBaseService<Users> {

	/**
	 * 更新密码
	 * @author 胡礼波
	 * 2012-6-7 下午01:24:25
	 * @param password 新密码
	 * @param userId 用户ID
	 * @return
	 */
	public boolean editPassword(int userId,String password);
	
	/**
	 * 用户登录
	 * @author 胡礼波
	 * 2012-5-18 下午07:52:41
	 * @param userName
	 * @param password
	 * @return
	 */
	public Users login(String userName,String password);
	
	/**
	 * 用户登出
	 * @author 胡礼波-Andy
	 * @2016年9月23日下午4:42:49
	 * @param userName
	 */
	public void logout(String userName);
	
	/**
	 * 根据用户名得到用户对象
	 * @author 胡礼波
	 * 2013-5-20 下午5:15:46
	 * @param userName
	 * @return
	 */
	public Users getUserByName(String userName);
	
	/**
	 * 修改用户帐号状态
	 * @author 胡礼波-Andy
	 * @2014年11月12日下午1:12:14
	 * @param userId 用户ID
	 * @return
	 */
	public boolean editUserEnable(int userId);
	
	/**
	 * 根据角色加载用户
	 * @author 胡礼波-Andy
	 * @2016年9月26日下午8:03:15
	 * @param roleId
	 * @return
	 */
	public List<Users> getUsersByRole(int roleId);
	
	/**
	 * 获得未分配角色的用户
	 * @author 胡礼波-Andy
	 * @2016年9月27日下午3:02:21
	 * @return
	 */
	public List<Users> getUsersForUnassignRole();
	
	/**
	 * 获得未分配角色的用户总数
	 * @author 胡礼波-Andy
	 * @2016年9月27日下午3:03:10
	 * @return
	 */
	public int getCountUsersForUnassignRole();
}
