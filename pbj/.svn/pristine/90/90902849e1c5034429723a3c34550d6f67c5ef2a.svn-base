package com.lw.iot.pbj.system.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lw.iot.pbj.common.constant.SysConst;
import com.lw.iot.pbj.core.annotation.ActionRightCtl;
import com.lw.iot.pbj.core.annotation.LoginFlag;
import com.lw.iot.pbj.core.base.action.BaseAction;
import com.lw.iot.pbj.logs.entity.LoginLogs;
import com.lw.iot.pbj.logs.service.ILoginLogsService;
import com.lw.iot.pbj.users.entity.Users;
import com.lw.iot.pbj.users.service.IUserService;

/**
 * 后台管理action
 * @author 胡礼波-Andy
 * @2015年4月20日下午2:29:45
 */
@Controller("ManageAction")
@RequestMapping(value="/manage")
public class SystemAction extends BaseAction {

	@Autowired
	private IUserService userService;
	@Autowired
	private ILoginLogsService loginLogsService;
	
	/**
	 * 后台首页
	 * @author 胡礼波-Andy
	 * @2016年9月5日下午6:03:33
	 * @return
	 */
	@RequestMapping(value="/index")
	public String execute()
	{
		return "index_";
	}
	
	/**
	 * 后台登陆界面
	 * @author 胡礼波-Andy
	 * @2016年9月5日下午6:03:40
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.NO)
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String toLogin()
	{
		return "login";
	}
	
	/**
	 * 用户登录
	 * @author 胡礼波-Andy
	 * @2014年11月10日上午10:37:42
	 * @param response
	 * @return
	 */
	@ActionRightCtl(login=LoginFlag.NO)
	@RequestMapping(value="/login",method=RequestMethod.POST)
	public String login(HttpServletResponse response)
	{
		HttpServletRequest request=getRequest();
		try
		{
			Users user=userService.login(request.getParameter("userName"),request.getParameter("password"));
			if(user!=null)
			{
				if(!user.isEnable())
				{
					request.setAttribute("errorMsg","该帐号已被禁用!");			
				}else
				{
					LoginLogs logs=loginLogsService.getLatestLog(user.getId(),2,LoginLogs.USERS_LOGIN);
					getSession().setAttribute(SysConst.LOGIN_FLAG,user);
					getSession().setAttribute("loginLogs",logs);
					return "redirect:/manage/index.html";
				}
			}else
			{
				request.setAttribute("errorMsg","用户名或密码错误!");
			}
		}catch(Exception e)
		{
			e.printStackTrace();
			request.setAttribute("errorMsg","系统异常"+e.getMessage());
		}
		return "login";
	}
	
	/**
	 * 安全退出
	 * @author 胡礼波-Andy
	 * @2014年11月12日上午11:47:28
	 * @return
	 */
	@RequestMapping(value="/logout")
	@ActionRightCtl(login=LoginFlag.NO)
	public String logOut()
	{
		Users user=getLoginedUser();
		if(user!=null)
		{
			userService.logout(user.getUserName());
		}
		getSession().invalidate();
		return "login";
	}
}
