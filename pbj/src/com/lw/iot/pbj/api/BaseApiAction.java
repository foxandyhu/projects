package com.lw.iot.pbj.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.lw.iot.pbj.api.entity.ResponseData;
import com.lw.iot.pbj.common.util.ResponseUtil;
import com.lw.iot.pbj.core.exception.SysErrorCodeEnum;

/**
 * 对外接口action基类
 * @author 胡礼波  andy_hulibo@163.com
 * @2017年4月18日 上午11:01:59
 */
@Controller("BaseApiAction")
public class BaseApiAction {

	/**
	 * 获得httpservletrequest
	 * @author 胡礼波
	 * 2012-4-26 下午07:51:00
	 * @return
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request=((ServletRequestAttributes)getRequestAttribute()).getRequest();
		return request;
	}

	/**
	 * 获得httpsession
	 * @author 胡礼波
	 * 2012-4-26 下午07:51:10
	 * @return
	 */
	public HttpSession getSession() {
		return getRequest().getSession();
	}
	
	/**
	 * 获取RequestAttributes
	 * @author 胡礼波-andy
	 * @2013-6-22下午2:23:52
	 * @return
	 */
	protected RequestAttributes getRequestAttribute()
	{
		RequestAttributes attr=RequestContextHolder.getRequestAttributes();
		return attr;
	}
	
	/**
	 * 获得设备序列号
	 * @author 胡礼波  andy_hulibo@163.com
	 * @2017年4月18日 上午11:03:44
	 * @return
	 */
	public String getSerialNo()
	{
		String seriesNo=String.valueOf(getRequest().getAttribute("serialno"));
		return seriesNo;
	}
	
	/**
	 * 获得版本号
	 * @author 胡礼波  andy_hulibo@163.com
	 * @2017年4月18日 上午11:04:38
	 * @return
	 */
	public String getVersion()
	{
		String version=String.valueOf(getRequest().getAttribute("version"));
		return version;
	}
	
	/**
	 * 获得内容体
	 * @author 胡礼波  andy_hulibo@163.com
	 * @2017年4月18日 上午11:05:14
	 * @return
	 */
	public String getContent()
	{
		String content=String.valueOf(getRequest().getAttribute("content"));
		return content;
	}
	
	/**
	 * 获得随机码
	 * @author 胡礼波  andy_hulibo@163.com
	 * @2017年4月18日 上午11:05:55
	 * @return
	 */
	public String getNonceStr()
	{
		String noncestr=String.valueOf(getRequest().getAttribute("noncestr"));
		return noncestr;
	}
	
	/**
	 * 获得签名
	 * @author 胡礼波  andy_hulibo@163.com
	 * @2017年4月18日 上午11:06:33
	 * @return
	 */
	public String getSignature()
	{
		String signature=String.valueOf(getRequest().getAttribute("signature"));
		return signature;
	}
	
	/**
	 * 统一异常处理
	 * @author 胡礼波  andy_hulibo@163.com
	 * @2017年4月18日 下午7:37:10
	 * @param request
	 * @param response
	 * @param ex
	 */
	@ExceptionHandler({Throwable.class})
	public void exceptionHandler(HttpServletRequest request,HttpServletResponse response,Exception ex)
	{
		String data=ResponseData.getFailResult(SysErrorCodeEnum.EXECUTE_ERROR,ex.getMessage());
		ResponseUtil.writeJson(response,data);
	}
}
