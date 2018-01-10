package com.bfly.trade.system.action;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bfly.trade.annotation.ActionRightCtl;
import com.bfly.trade.base.action.BaseAction;
import com.bfly.trade.enums.LoginFlag;
import com.bfly.trade.exception.NeedLoginException;
import com.bfly.trade.logs.entity.LoginLogs;
import com.bfly.trade.logs.service.ILoginLogsService;
import com.bfly.trade.users.entity.Users;
import com.bfly.trade.users.service.IUsersService;
import com.bfly.trade.util.SysConst;

/**
 * 后台管理action
 * @author 胡礼波-Andy
 * @2015年4月20日下午2:29:45
 */
@Controller("SystemAction")
@RequestMapping()
public class SystemAction extends BaseAction {

	@Autowired
	private IUsersService userService;
	@Autowired
	private ILoginLogsService loginLogsService;
	
	private Logger logger=Logger.getLogger(SystemAction.class);
	
	/**
	 * 用户登录
	 * @author 胡礼波-Andy
	 * @2014年11月10日上午10:37:42
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@ActionRightCtl(login=LoginFlag.NO)
	@RequestMapping(value="/submit",method=RequestMethod.POST)
	public void login(HttpServletResponse response) throws Exception
	{
		Users user=userService.login(getRequest().getParameter("userName"),getRequest().getParameter("password"));
		if(user!=null)
		{
			if(!user.isEnable())
			{
				logger.warn(String.format("the %s account is disabled!",user.getUserName()));
				throw new NeedLoginException(String.format("the %s account is disabled!",user.getUserName()));
			}
			LoginLogs logs=loginLogsService.getLatestLog(user.getId(),2,LoginLogs.USERS_LOGIN);
			getSession().setAttribute(SysConst.LOGIN_FLAG,user);
			getSession().setAttribute("loginLogs",logs);
//				UsersRightContainer.loadUserRight(user.getId());
			response.sendRedirect("index.html");
		}else
		{
			logger.warn("userName or password is error!");
			throw new NeedLoginException("userName or password is error!");
		}
	}
	
	/**
	 * 安全退出
	 * @author 胡礼波-Andy
	 * @2014年11月12日上午11:47:28
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/logout")
	@ActionRightCtl(login=LoginFlag.NO)
	public void logOut(HttpServletResponse response) throws Exception
	{
		Users user=getLoginedUser();
		if(user!=null)
		{
			userService.logout(user.getUserName());
//			UsersRightContainer.clear(user.getId());
		}
		getSession().invalidate();
		response.sendRedirect("login.html");
	}
}
