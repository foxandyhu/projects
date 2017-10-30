package com.lw.iot.pbj.core.interceptor;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lw.iot.pbj.common.constant.SysConst;
import com.lw.iot.pbj.common.util.ContextUtil;
import com.lw.iot.pbj.member.entity.Member;

/**
 * 微信权限验证拦截器
 * @author 胡礼波
 * 2013-6-26 上午10:32:08
 */
public class MemberSecurityInterceptor extends HandlerInterceptorAdapter{

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
			if (!hasLogined())
			{
				dispatchLoginRequest(request, response);
				return false;
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
		String contextPath=request.getContextPath();
		contextPath=contextPath+"/api/weixin/authorize.html";
		if(SysConst.AJAXREQUEST.equals(xmlRquest))
		{
			//未登录
			response.setStatus(401);
		}else
		{
			response.sendRedirect(contextPath);
		}
	}
	
	/**
	 * 判断是否登录
	 * @author 胡礼波
	 * 2012-4-25 下午06:32:37
	 * @return
	 */
	private boolean hasLogined()
	{
		Member member = ContextUtil.getContextLoginMember();
		return member == null ? false : true;
	}
	
}
