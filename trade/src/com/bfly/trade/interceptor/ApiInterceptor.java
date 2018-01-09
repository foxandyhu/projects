package com.bfly.trade.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Api拦截器
 * @author andy_hulibo@163.com
 * 2018年1月8日下午2:34:59
 */
@Component
public class ApiInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		System.out.println("APIInterceptor>>>>>>>>>>>>>>>>>>>>>>>>");
		return super.preHandle(request, response, handler);
	}
}
