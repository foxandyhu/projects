package com.lw.iot.pbj.users.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lw.iot.pbj.common.util.ContextUtil;
import com.lw.iot.pbj.common.util.StringUtil;
import com.lw.iot.pbj.core.annotation.ActionModel;
import com.lw.iot.pbj.core.annotation.LogRecord;
import com.lw.iot.pbj.core.annotation.RecordType;
import com.lw.iot.pbj.core.base.service.impl.BaseServiceImpl;
import com.lw.iot.pbj.core.encoder.Md5PwdEncoder;
import com.lw.iot.pbj.logs.entity.LoginLogs;
import com.lw.iot.pbj.logs.service.ILogService;
import com.lw.iot.pbj.logs.service.ILoginLogsService;
import com.lw.iot.pbj.users.entity.Users;
import com.lw.iot.pbj.users.persistence.UserMapper;
import com.lw.iot.pbj.users.service.IUserService;

/**
 * 系统用户业务实现类
 * @author andy_hulibo@163.com
 * @2017年10月31日 下午2:37:26
 */
@Service("UserServiceImpl")
@Transactional(propagation=Propagation.SUPPORTS,rollbackFor=Exception.class)
@ActionModel(description="用户管理")
public class UserServiceImpl extends BaseServiceImpl<Users> implements IUserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ILogService logService;
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
	@Transactional(rollbackFor=Exception.class)
	@ActionModel(description="用户注册")	
	public int save(Users user)
	{
		checkUserProperty(user);
		//账号状态启用
		user.setEnable(true);
		user.setRegistDate(new Date());
		String password=user.getPassword();
		//产生9位的加密salt
		String salt=StringUtil.getRandomString(9);
		//密码加密
		String pwd=new Md5PwdEncoder().encodePassword(password,salt);
		user.setPassword(pwd);
		user.setSalt(salt);
		user.setRegistIp(ContextUtil.getClientIp());
		int count=userMapper.save(user);
		return count;
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
	@Transactional(rollbackFor=Exception.class)
	@ActionModel(description="编辑用户")
	@Override
	public int edit(Users user)
	{	if(user.isEnable()!=true){
			user.setEnable(false);
		}else{
			user.setEnable(true);
		}
		int count=userMapper.editById(user);
		return count;
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
		if(getUserByName(user.getUserName())!=null)
		{
			throw new NullPointerException("用户名已存在,请更换用户名!");
		}
		if(StringUtils.isEmpty(StringUtil.trimHtmlTag(user.getPassword().trim())))
		{
			throw new NullPointerException("密码为空!");
		}
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	@ActionModel(description="修改密码")
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
	@ActionModel(description="用户登录")
	@Transactional(rollbackFor=Exception.class)
	@LogRecord(record=RecordType.IGNORE)
	public Users login(String userName, String password) {
		Users user=getUserByName(userName);
		if(user!=null)
		{
			if(new Md5PwdEncoder().isPasswordValid(user.getPassword(),password,user.getSalt()))
			{
				String ip=ContextUtil.getClientIp();
				logService.operating(user, ip, "用户管理", "用户登录",user.getUserName()+"用户登录成功");
				saveLoginLogs(user.getUserName(),ip,true);
				return user;
			}
		}
		return null;
	}

	@Override
	@ActionModel(description="用户登出")
	@Transactional(rollbackFor=Exception.class)
	@LogRecord(record=RecordType.IGNORE)
	public void logout(String userName) {
		String ip=ContextUtil.getClientIp();
		saveLoginLogs(userName,ip,false);
	}
	
	/**
	 * 保存登录日志
	 * @author 胡礼波-Andy
	 * @2016年9月6日上午10:43:51
	 * @param userName
	 * @param ip
	 */
	@Transactional(rollbackFor=Exception.class)
	private void saveLoginLogs(String userName,String ip,boolean isLogin)
	{
		LoginLogs logs=new LoginLogs(userName, ip, LoginLogs.USERS_LOGIN,isLogin);
		loginLogsService.save(logs);
	}
	
	@Override
	public Users getUserByName(String userName) {
		Assert.hasText(userName,"用户名为空!");
		return userMapper.getUserByName(userName);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@ActionModel(description="删除用户")
	public int del(Integer... id) {
		if(ArrayUtils.isEmpty(id))
		{
			return 0;
		}
		Users user=ContextUtil.getContextLoginUser();
		for (Integer userId : id) {
			//不能删除当前登录用户
			if(userId!=user.getId())
			{
				return super.del(id);
			}
		}
		return 0;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@ActionModel(description="修改帐号状态")
	public boolean editUserEnable(int userId) {
		Users user=get(userId);
		if(user==null)
		{
			return false;
		}
		//不能操作当前登录用户
		if(user.getId()==ContextUtil.getContextLoginUser().getId())
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
