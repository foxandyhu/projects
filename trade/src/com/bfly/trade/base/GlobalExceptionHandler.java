package com.bfly.trade.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bfly.trade.exception.NeedLoginException;
import com.bfly.trade.exception.NoPermissionToAccessResourceException;
import com.bfly.trade.util.ResponseUtil;

/**
 * Controller全局异常处理
 * @author andy_hulibo@163.com
 * @2018年1月10日 上午10:35:11
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({Throwable.class})
	public void exceptionHandler(HttpServletRequest request,HttpServletResponse response,Exception ex)
	{
		if(ex instanceof NeedLoginException)
		{
			response.setStatus(401);				//未登录
		}else if(ex instanceof NoPermissionToAccessResourceException)
		{
			response.setStatus(403);				//未登录
		}else
		{
			response.setStatus(500);
		}
		ResponseUtil.writeText(response, ex.getMessage());
	}
}
