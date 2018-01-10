package com.bfly.trade.users.service.impl;

import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bfly.trade.annotation.ActionModel;
import com.bfly.trade.base.service.impl.BaseServiceImpl;
import com.bfly.trade.codes.Md5PwdEncoder;
import com.bfly.trade.logs.entity.LoginLogs;
import com.bfly.trade.logs.service.ILoginLogsService;
import com.bfly.trade.users.entity.Users;
import com.bfly.trade.users.mapper.UsersMapper;
import com.bfly.trade.users.service.IUsersService;
import com.bfly.trade.util.ContextUtil;
import com.bfly.trade.util.StringUtil;

@Service("UserServiceImpl")
@Transactional(propagation=Propagation.SUPPORTS)
@ActionModel("用户管理")
public class UsersServiceImpl extends BaseServiceImpl<Users> implements IUsersService {

	@Autowired
	private UsersMapper userMapper;
	@Autowired
	private ILoginLogsService loginLogsService;
	
	/**
	 * 新增用户
	 * @author 胡礼波
	 * 2014-7-21 下午9:29:53
	 * @param user
	 * @return
	 */
	@Override
	@Transactional
	@ActionModel("用户注册")	
	public boolean save(Users user)
	{
		checkUserProperty(user);
		user.setEnable(true);					//账号状态启用
		String password=user.getPassword();
		String salt=StringUtil.getRandomString(9);					//产生9位的加密salt
		String pwd=new Md5PwdEncoder().encodePassword(password,salt);	//密码加密
		user.setPassword(pwd);
		user.setSalt(salt);
		return userMapper.save(user) > 0;
	}
	
	@Override
	public List<Users> getUsersForUnassignRole() {
		return userMapper.getUsersForUnassignRole(ContextUtil.getThreadLocalPagerMap());
	}

	@Override
	public int getCountUsersForUnassignRole() {
		return userMapper.getCountUsersForUnassignRole();
	}

	/**
	 * 编辑用户
	 * @author 胡礼波-Andy
	 * @2015年1月20日下午1:23:31
	 * @param user
	 * @return 
	 */
	@Transactional
	@ActionModel("编辑用户")
	@Override
	public boolean edit(Users user)
	{	if(user.isEnable()!=true){
			user.setEnable(false);
		}else{
			user.setEnable(true);
		}
		return userMapper.editById(user)>0 ;
	}
	
	/**
	 * 对用户对象部分属性校验
	 * @author 胡礼波
	 * 2014-7-29 下午8:50:44
	 * @param user
	 */
	private void checkUserProperty(Users user)
	{
		if(user==null)
		{
			throw new NullPointerException("注册信息为空,请重新注册！");
		}
		if(StringUtils.isEmpty(user.getUserName()))
		{
			throw new NullPointerException("用户名为空,请输入用户名!");
		}
		if(getUserByName(user.getUserName())!=null)		//用户名被占用 服务器端校验
		{
			throw new NullPointerException("用户名已存在,请更换用户名!");
		}
		if(StringUtils.isEmpty(StringUtil.trimHtmlTag(user.getPassword().trim())))
		{
			throw new NullPointerException("密码为空!");
		}
	}
	
	@Override
	@Transactional
	@ActionModel("修改密码")
	public boolean editPassword(int userId, String password) {
		Assert.hasText(password, "新密码为空！");
		Assert.isTrue(!password.contains(" "),"密码不能包含空格!");
		
		String salt=StringUtil.getRandomString(9);
		
		Users user=get(userId);
		Assert.notNull(user,"用户信息不存在!");
		user.setPassword(new Md5PwdEncoder().encodePassword(password,salt));
		user.setSalt(salt);
		return userMapper.editById(user)>0;
	}

	@Override
	@ActionModel("用户登录")
	@Transactional
	public Users login(String userName, String password) {
		Users user=getUserByName(userName);
		if(user!=null)
		{
			if(new Md5PwdEncoder().isPasswordValid(user.getPassword(),password,user.getSalt()))	//密码判断
			{
				String ip=ContextUtil.getClientIp();
				saveLoginLogs(user.getUserName(),ip,true);				//更新登录日志
				return user;
			}
		}
		return null;
	}

	@Override
	@ActionModel("用户登出")
	@Transactional
	public void logout(String userName) {
		String ip=ContextUtil.getClientIp();
		saveLoginLogs(userName,ip,false);							//更新登录日志
	}
	
	/**
	 * 保存登录日志
	 * @author 胡礼波-Andy
	 * @2016年9月6日上午10:43:51
	 * @param userName
	 * @param ip
	 */
	@Transactional
	private void saveLoginLogs(String userName,String ip,boolean isLogin)
	{
		LoginLogs logs=new LoginLogs(userName, ip, LoginLogs.USERS_LOGIN,isLogin);
		loginLogsService.save(logs);
	}
	
	@Override
	public Users getUserByName(String userName) {
		Assert.hasText(userName,"userName is empty!");
		return userMapper.getUserByName(userName);
	}

	@Override
	@Transactional
	@ActionModel("删除用户")
	public int del(Integer... id) {
		if(ArrayUtils.isEmpty(id))
		{
			return 0;
		}
		Users user=ContextUtil.getContextLoginUser();
		for (Integer userId : id) {
			if(userId!=user.getId())		//不能删除当前登录用户
			{
				return super.del(id);
			}
		}
		return 0;
	}

	@Override
	@Transactional
	@ActionModel("修改帐号状态")
	public boolean editUserEnable(int userId) {
		Users user=get(userId);
		if(user==null)
		{
			return false;
		}
		if(user.getId()==ContextUtil.getContextLoginUser().getId())	//不能操作当前登录用户
		{
			return false;
		}
		user.setEnable(!user.isEnable());
		return userMapper.editById(user)>0;
	}

	@Override
	public List<Users> getUsersByRole(int roleId) {
		return userMapper.getUsersByRole(roleId);
	}
}
