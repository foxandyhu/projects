package com.bfly.trade.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 登录验证拦截器
 * @author andy_hulibo@163.com
 * 2018年1月8日下午2:38:59
 */
@Component
public class LoginInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("LoginInterceptor>>>>>>>>>>>>>>>>>>>>>>>>");
		return super.preHandle(request, response, handler);
	}
}
