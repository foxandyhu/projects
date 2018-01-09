package com.lw.iot.pbj.core.exception;

/**
 * 系统错误状态码枚举
 * @author 胡礼波-Andy
 * @2015年1月14日上午11:33:21
 */
public enum SysErrorCodeEnum {

	/**
	 * 授权码为空
	 */
	AUTHORKEY_ISNULL("SYS002","Authorization code is empty"),
	
	/**
	 * 授权码错误
	 */
	AUTHORKEY_ISERROR("SYS003","Authorization code error"),
	
	/**
	 * 非法服务器请求
	 */
	PERMISSION_DENIED("SYS004","Illegal server request"),
	
	/**
	 * 系统连接错误
	 */
	CONNECT_ERROR("SYS005","System connection error"),
	
	/**
	 * 系统未响应
	 */
	SYS_UNRESPONSE("SYS006","System is not responding"),
	
	/**
	 * 系统内部异常
	 */
	SYS_INNER_ERROR("SYS007","System internal exception"),
	
	/**
	 * 请求拒绝
	 */
	REQUEST_REFUSE("SYS008","Request reject"),
	
	/**
	 * 请求处理失败
	 */
	EXECUTE_ERROR("SYS009","Request processing failed"),
	
	/**
	 * 参数错误
	 */
	PARAM_ERRPR("SE002","Parameter error"),
	
	/**
	 * 缺少参数
	 */
	PARAM_LESS("SE003","Missing parameters"),
	
	/**
	 * 无效的操作指令
	 */
	CMD_INVALID("CMD002","Invalid operation instruction"),
	
	/**
	 * 操作指令为空
	 */
	CMD_ISNULL("CMD003","Operation instruction is empty"),
	
	/**
	 * 版本过低
	 */
	VERSION_LOWER("V0010","Version is too low");
	
	private String code;
	private String message;

	private SysErrorCodeEnum(String code,String message)
	{
		this.code=code;
		this.message=message;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
