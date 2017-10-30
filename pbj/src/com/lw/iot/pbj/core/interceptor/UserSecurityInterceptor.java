package com.lw.iot.pbj.core.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lw.iot.pbj.common.util.ContextUtil;
import com.lw.iot.pbj.common.util.ResponseUtil;
import com.lw.iot.pbj.common.util.reflect.ReflectUtils;
import com.lw.iot.pbj.core.annotation.ActionRightCtl;
import com.lw.iot.pbj.core.annotation.LoginFlag;
import com.lw.iot.pbj.users.entity.Users;


/**
 * 权限验证拦截器
 * @author 胡礼波
 * 2013-6-26 上午10:32:08
 */
public class UserSecurityInterceptor extends HandlerInterceptorAdapter{

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
					dispatchLoginRequest(request, response);
					return false;
				}
				if(!hasRight(user,request))
				{
					response.setStatus(HttpStatus.FORBIDDEN.value());				//访问资源未授权
					ResponseUtil.writeText(response, "访问资源未授权!");
					return false;
				}
			}	
		}
		return true;
	}
	
	/**
	 * 识别Http请求类型做出对应的动作
	 * @author 胡礼波
	 * 2013-6-26 上午11:20:05
	 * @param requst
	 */
	public void dispatchLoginRequest(HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		String xmlRquest=request.getHeader("X-Requested-With");
		if("XMLHttpRequest".equals(xmlRquest))			//表示异步Ajax请求弹出登录框
		{
			response.setStatus(401);					//未登录
		}else
		{
			String contextPath=request.getContextPath();
			response.sendRedirect(contextPath+"/manage/login.html");
		}
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
