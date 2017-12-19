package com.comm.server.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.comm.server.entity.ResponseData;
import com.comm.server.util.JsonUtil;
import com.comm.server.util.ResponseUtil;

/**
 * 全局异常处理
 * @author andy_hulibo@163.com
 * @2017年11月30日 下午4:37:16
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({Throwable.class})
	public void exceptionHandler(HttpServletRequest request,HttpServletResponse response,Exception ex)
	{
		ResponseData data=ResponseData.getFailData("", ex.getMessage(), "");
		String json=JsonUtil.toJsonStringFilterPropter(data).toJSONString();
		
		ResponseUtil.writeJson(response, json);
	}
}
