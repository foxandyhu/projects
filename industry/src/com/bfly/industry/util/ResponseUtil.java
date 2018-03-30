package com.bfly.industry.util;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * HttpServletResponse响应工具类
 * @author 胡礼波
 * 2013-6-26 上午11:23:53
 */
public class ResponseUtil {

	private static Logger logger=Logger.getLogger(ResponseUtil.class);

	/**
	 * 向客户端输出javascript脚本
	 * @author 胡礼波-andy
	 * @2013-6-23下午3:49:10
	 * @param response
	 * @param jsText
	 */
	public static void writeJavascript(HttpServletResponse response,String jsText)
	{
		writeData(response,jsText,"application/javascript");
	}
	
	/**
	 * 输出文本字符串
	 * @author 胡礼波-andy
	 * @2013-6-22下午8:33:35
	 * @param response
	 * @param text
	 */
	public static void writeHtml(HttpServletResponse response,Object text)
	{
		writeData(response,String.valueOf(text),"text/html");
	}
	
	/**
	 * 输出文本字符串
	 * @author 胡礼波-andy
	 * @2013-6-22下午8:33:35
	 * @param response
	 * @param text
	 */
	public static void writeText(HttpServletResponse response,Object text)
	{
		writeData(response,String.valueOf(text),"text/plain");
	}
	
	/**
	 * 输出xml
	 * @author 胡礼波-andy
	 * @2013-6-22下午8:34:29
	 * @param response
	 * @param xml
	 */
	public static void writeXml(HttpServletResponse response,String xml)
	{
		writeData(response, xml,"application/xmlapplication/xml");
	}
	
	/**
	 * 输出Json
	 * @author 胡礼波-andy
	 * @2013-6-22下午8:35:08
	 * @param response
	 * @param json
	 */
	public static void writeJson(HttpServletResponse response,String json)
	{
		writeData(response,json, "application/json");
	}
	
	/**
	 * 输出数据 通常是Ajax调用
	 * @author 胡礼波
	 * 2012-4-28 下午09:11:53
	 * @param ojb
	 */
	private static void writeData(HttpServletResponse response,String data,String contentType)
	{
		response.setCharacterEncoding(SysConst.ENCODEING_UTF8);
		response.setContentType(contentType);
		try(PrintWriter out=response.getWriter())
		{
			String dataStr=String.valueOf(data);
			out.write(dataStr);
			out.flush();
		}catch (Exception e) {
			logger.warn("数据流输入有错:"+e);
		}
	}

}
