package com.bfly.trade.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.bfly.trade.annotation.ActionRightCtl;
import com.bfly.trade.enums.LoginFlag;
import com.bfly.trade.exception.NeedLoginException;
import com.bfly.trade.exception.NoPermissionToAccessResourceException;
import com.bfly.trade.users.entity.Users;
import com.bfly.trade.util.ContextUtil;
import com.bfly.trade.util.ReflectUtils;

/**
 * 登录验证拦截器
 * @author andy_hulibo@163.com
 * 2018年1月8日下午2:38:59
 */
public class LoginInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception {
		if(handler instanceof HandlerMethod)
		{
			HandlerMethod handlerMethod=(HandlerMethod)handler;
			Method method= handlerMethod.getMethod();
			if(method==null)
			{
				return false;
			}
			if(needLogin(method))
			{
				Users user = ContextUtil.getContextLoginUser();
				if (!hasLogined(user))
				{
					throw new NeedLoginException();
				}
				if(!hasRight(user,request))
				{
					throw new NoPermissionToAccessResourceException();
				}
			}	
		}
		return true;
	}
	
	/**
	 * 判断是否登录
	 * @author 胡礼波
	 * 2012-4-25 下午06:32:37
	 * @return
	 */
	private boolean hasLogined(Users user)
	{
		return user == null ? false : true;
	}
	
	/**
	 * 判断是否有权限访问该资源
	 * @author 胡礼波-Andy
	 * @2015年12月14日下午5:35:27
	 * @return
	 */
	private boolean hasRight(Users user,HttpServletRequest request)
	{
		return true;				//临时放行
//		String url=String.valueOf(request.getAttribute("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping"));
//		if(UsersRightContainer.exist(user.getId(),url))												//先判断真实请求的URL地址
//		{
//			return true;
//		}
//		url=String.valueOf(request.getAttribute("org.springframework.web.servlet.HandlerMapping.bestMatchingPattern"));//再判断模糊匹配URL
//		return UsersRightContainer.exist(user.getId(),url);
	}
	
	/**
	 * 判断资源是否需要登录
	 * @author 胡礼波
	 * 2012-4-25 下午06:32:47
	 * @param mth
	 * @return
	 */
	private boolean needLogin(Method mth)
	{
			ActionRightCtl arc = ReflectUtils.getActionAnnotationValue(mth);
			if (arc == null)
			{
				return true;
			}
			LoginFlag rightName = arc.login();
			return rightName == LoginFlag.YES ? true : false;
	}
}
