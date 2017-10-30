package com.lw.iot.pbj.common.constant;


/**
 * 系统常量
 * @author 胡礼波-Andy
 * @2014年11月10日上午9:36:18
 */
public class SysConst {
    
	public final static String MEMBER_LOGIN_FLAG="loginMember";					//用户登录标识
	public final static String LOGIN_FLAG="loginUser";					//用户登录标识
	public final static String ENCODEING_UTF8="UTF-8";				//编码
    public final static String COMMON_SECRET_KEY = "@1SF^!*$";				//公共数据的加密密钥
	
	public final static String FIRSTRESULT="firstResult";		//从第几条记录开始
	public final static String MAXRESULT="maxResult";			//到第几条记录结束
	public final static String PAGENO="pageNo";					//页码
	public final static String PAGESIZE="pageSize";				//每页记录条数
	
	public final static String IP="ip";							//用户IP
	public final static String USERID="userId";					//用户ID
	
	public final static int MAX_IMG_SIZE=2;						//上传图片最大
	public final static int PAY_TIMEOUT=5;						//支付时间限制5分钟
	
	public static String appServer=null;						//网站地址
	public static String picServer=null;						//图片服务器地址
	public static String resourcePath=null;						//文件资源绝对路径
	public static String tempPath=null;							//文件资源临时路径	
}


