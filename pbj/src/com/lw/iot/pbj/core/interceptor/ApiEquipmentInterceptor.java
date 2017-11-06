package com.lw.iot.pbj.core.interceptor;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.lw.iot.pbj.api.entity.RequestData;
import com.lw.iot.pbj.common.util.SecurityUtil;
import com.lw.iot.pbj.equipment.entity.PedometerReader;
import com.lw.iot.pbj.equipment.service.IPedometerReaderService;

/**
 * 智能柜权限验证拦截器
 * @author 胡礼波-Andy
 * @2016年4月21日上午11:47:36
 */
public class ApiEquipmentInterceptor extends HandlerInterceptorAdapter{

	private Logger logger=Logger.getLogger(ApiEquipmentInterceptor.class);
	
	@Autowired
	private IPedometerReaderService pedometerReaderService;
	
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
			RequestData data=getRequestData(request);
			
			checkValidCabinet(data);
			if(!checkDataSign(data))
			{
				throw new RuntimeException("data signature error!");
			}
			try {
				pedometerReaderService.editLastComm(data.getSerialNo(),new Date());
			} catch (Exception e) {
				logger.error("edit equipment communication data is error",e);
			}
		}
		return true;
	}


	/**
	 * 组装Request数据
	 * @author 胡礼波  andy_hulibo@163.com
	 * @2017年4月18日 上午10:46:58
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	private RequestData getRequestData(HttpServletRequest request) throws Exception
	{
		String dataStr =IOUtils.toString(request.getInputStream());
		String[] datas=dataStr.split(",");
		
		String version=datas[1];
		String signature=datas[datas.length-1];
		String noncestr=datas[4];
		String serialNo=datas[0];
		
		request.setAttribute("version",version);
		request.setAttribute("serialno",serialNo);
		request.setAttribute("noncestr",noncestr);
		
		StringBuilder sb=new StringBuilder();
		int len=datas.length-1;
		for (int i=5;i<len;i++) {
			sb.append(datas[i]);
			if(i+1<len)
			{
				sb.append(",");
			}
		}
		
		String content=sb.toString();
		request.setAttribute("content",content);
		
		
		RequestData data=new RequestData();
		data.setContent(content);
		data.setNonceStr(noncestr);
		data.setSerialNo(serialNo);
		data.setSignature(signature);
		data.setVersion(version);
		return data;
	}
	
	/**
	 * 授权检查
	 * @author 胡礼波-Andy
	 * @2016年4月21日上午11:37:33
	 * @param data
	 * @return
	 */
	private boolean checkValidCabinet(RequestData data)
	{
		PedometerReader reader=pedometerReaderService.getPedometerReader(data.getSerialNo());
		if(reader==null)
		{
			logger.warn("Invalid request from "+data.getSerialNo());
			throw new RuntimeException("Invalid request");
		}
		if(!reader.isEnable())
		{
			logger.warn("the equipment has been disabled ["+data.getSerialNo()+"]");
			throw new RuntimeException("the equipment has been disabled");
		}
		return true;
	}
	
	/**
	 * 检查数据签名
	 * @author 胡礼波  andy_hulibo@163.com
	 * @2017年4月18日 上午10:43:28
	 * @param data
	 * @return
	 */
	private boolean checkDataSign(RequestData data)
	{
		String signature = SecurityUtil.sha1(data.getSerialNo()+data.getContent()+data.getNonceStr()+data.getVersion()).toUpperCase(Locale.CHINA);
		return signature.equals(data.getSignature());
	}
}
