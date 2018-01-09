package com.lw.iot.pbj.api.entity;

import java.io.IOException;
import java.io.Serializable;

import com.lw.iot.pbj.common.constant.SysConst;
import com.lw.iot.pbj.common.json.JsonUtil;
import com.lw.iot.pbj.common.util.SecurityUtil;
import com.lw.iot.pbj.core.exception.SysErrorCodeEnum;

/**
 * HTTP请求数据返回封装类
 * @author andy_hulibo
 * 2017年4月17日下午6:11:13
 */
public class ResponseData implements Serializable{
	
	private static final long serialVersionUID = -6507444341663513311L;
	
	private static final int SUCCESS = 1;
	
	private static final int FAILURE = -1;
	
	/**
	 * 是否加密
	 */
	private boolean encrypt;
	
	/**
	 * 错误码
	 */
	private String errorCode;
	
	/**
	 * 消息实体
	 */
	private Object message;
	
	/**
	 * 处理状态 1成功 -1失败
	 */
	private int status;
	
	public ResponseData(){
	}
	
	/**
	 * 请求成功，返回未加密的JSON数据
	 * @author andy_hulibo
	 * 2017年4月17日下午6:28:09
	 * @param code
	 * @param msg
	 * @return
	 */
	public static String getSuccessResult(String code,Object msg){
		return JsonUtil.toJsonStringFilterPropter(new ResponseData(false,code,msg,SUCCESS),"").toJSONString();
	}
	
	/**
	 * 请求成功，返回未加密的对象
	 * @author 胡礼波  andy_hulibo@163.com
	 * @2017年8月15日 上午9:45:47
	 * @param code
	 * @param msg
	 * @return
	 */
	public static ResponseData getSuccess(String code,Object msg){
		return new ResponseData(false,code,msg,SUCCESS);
	}	
	
	/**
	 * 请求成功，返回加密的JSON数据
	 * @author andy_hulibo
	 * 2017年4月17日下午6:28:39
	 * @param code
	 * @param msg
	 * @return
	 * @throws IOException
	 */
	public static String getSuccessSsResult(String code,Object msg) throws IOException{
		String res = JsonUtil.toJsonStringFilterPropter(new ResponseData(true,code,msg,SUCCESS),"").toJSONString();
		return SecurityUtil.aESEnCoding(res,SysConst.COMMON_SECRET_KEY);
	}
	
	/**
	 * 请求失败，返回未加密的JSON数据
	 * @author andy_hulibo
	 * 2017年4月17日下午6:29:34
	 * @param code
	 * @param msg
	 * @return
	 */
	public static String getFailResult(SysErrorCodeEnum code,Object msg){
		return JsonUtil.toJsonStringFilterPropter(new ResponseData(false,code.getCode(),msg,FAILURE),"").toJSONString();
	}
	
	/**
	 * 请求失败，返回未加密的对象
	 * @author 胡礼波  andy_hulibo@163.com
	 * @2017年8月15日 上午9:47:03
	 * @param code
	 * @param msg
	 * @return
	 */
	public static ResponseData getFail(SysErrorCodeEnum code,Object msg){
		return new ResponseData(false,code.getCode(),msg,FAILURE);
	}
	
	/**
	 * 请求失败，返回未加密的JSON数据
	 * @author andy_hulibo
	 * 2017年4月17日下午6:29:55
	 * @param code
	 * @param msg
	 * @return
	 * @throws IOException
	 */
	public static String getFailSsResult(SysErrorCodeEnum code,Object msg) throws IOException{
		String res = JsonUtil.toJsonStringFilterPropter(new ResponseData(true,code.getCode(),msg,FAILURE),"").toJSONString();;
		return SecurityUtil.aESEnCoding(res,SysConst.COMMON_SECRET_KEY);
	}
	
	public ResponseData(boolean en,String code,Object msg,int status){
		this.encrypt = en;
		this.errorCode = code;
		this.message = msg;
		this.status = status;				
	}
	
	public boolean isEncrypt() {
		return encrypt;
	}
	
	public void setEncrypt(boolean encrypt) {
		this.encrypt = encrypt;
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public Object getMessage() {
		return message;
	}
	
	public void setMessage(Object message) {
		this.message = message;
	}
	
	public int getStatus() {
		return status;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}
	
	public static void main(String[] args) {
		ResponseData data=ResponseData.getSuccess("","");
		System.out.println(JsonUtil.toJsonStringFilterPropter(data));
	}
}
