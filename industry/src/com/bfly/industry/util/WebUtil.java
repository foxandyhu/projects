package com.bfly.industry.util;

/**
 * web工具
 * @author andy_hulibo@163.com
 * @2018年4月8日 下午2:23:35
 */
public class WebUtil {

	private static final String ID_KEY="ids_bfly";
	
	/**
	 * ID字符串解码转换
	 * @author andy_hulibo@163.com
	 * @2018年4月8日 下午2:25:50
	 * @param idStr
	 * @return
	 */
	public static int IdConvert(String idStr)
	{
		byte bytes[]=SecurityUtil.AESDecoding(idStr, ID_KEY);
		idStr=new String(bytes);
		int id=DataConvertUtils.convertToInteger(idStr);
		return id;
	}
	
	/**
	 * ID数字加密转换
	 * @author andy_hulibo@163.com
	 * @2018年4月8日 下午2:28:06
	 * @param id
	 * @return
	 */
	public static String IdConvert(int id)
	{
		String idStr=SecurityUtil.AESEncoding(String.valueOf(id),ID_KEY);
		return idStr;
	}
}
