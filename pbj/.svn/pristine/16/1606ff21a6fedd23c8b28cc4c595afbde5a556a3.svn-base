package com.lw.iot.pbj.core.interceptor;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
				pedometerReaderService.editCommLocation(data.getSerialNo(),data.getLongitude(),data.getLatitude());
			} catch (Exception e) {
				logger.error("edit equipment communication data is error",e);
			}
			request.setAttribute("requestData", data);
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
		String longitude=datas[2];
		String latitude=datas[3];
		
		String regex = "(?<=\\[)(\\S+)(?=\\])";
		Pattern pattern = Pattern.compile (regex);
		Matcher matcher = pattern.matcher (dataStr);
		String content=null;
		if(matcher.find())
		{
			content=matcher.group ();
		}
		
		RequestData data=new RequestData();
		data.setContent(content);
		data.setNonceStr(noncestr);
		data.setSerialNo(serialNo);
		data.setSignature(signature);
		data.setVersion(version);
		data.setLatitude(latitude);
		data.setLongitude(longitude);
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
		String signature = SecurityUtil.sha1(data.getSerialNo()+"["+data.getContent()+"]"+data.getNonceStr()+data.getVersion()).toUpperCase(Locale.CHINA);
		return signature.equals(data.getSignature());
	}
	
	public static void main(String[] args) {
//		String data="666666,1.0,12.232,11.234234,QWE123ASD12,[123456:11:1000:2000:1.0:2017-10-10,123456:11:3000:4000:1.0:2017-10-11,123456:11:100:500:1.0:2017-10-12],F1A9EF51D48F864FADDECDF366292EDDF35B4250";
		String sig="666666[123456:11:1000:2000:1.0:2017-10-10,123456:11:3000:4000:1.0:2017-10-11,123456:11:100:500:1.0:2017-10-12]QWE123ASD121.0";
		System.out.println(SecurityUtil.sha1(sig).toUpperCase(Locale.CHINA));
	}
}
