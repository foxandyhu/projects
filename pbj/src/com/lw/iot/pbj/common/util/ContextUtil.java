package com.lw.iot.pbj.common.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.lw.iot.pbj.common.constant.SysConst;
import com.lw.iot.pbj.common.page.Pager;
import com.lw.iot.pbj.users.entity.Users;

/**
 * 系统上下文工具类
 * @author 胡礼波
 * 2012-11-1 上午10:59:08
 */
public class ContextUtil {

	private static Logger logger=Logger.getLogger(ContextUtil.class);

	/**
	 * 从springcontext中获取某个类型的对象实例
	 * @author 胡礼波-andy
	 * @2013-6-22下午3:57:35
	 * @param c
	 * @return
	 */
	public static <T> T getWebContextBean(Class<T> c,ServletContext servletCtx)
	{
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(servletCtx);
		return ctx.getBean(c);
	}
	
	/**
	 * 从springcontext中获取某个类型的对象实例
	 * @author 胡礼波-andy
	 * @2013-6-22下午3:57:35
	 * @param c
	 * @return
	 */
	public static Object getWebContextBean(Class<?> c)
	{
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		return ctx.getBean(c);
	}
	
	/**
	 * 从springcontext中获取某个类型的对象实例
	 * @author 胡礼波-andy
	 * @2013-6-22下午3:57:35
	 * @param c name
	 * @return
	 */
	public static Object getWebContextBean(String name,Class<?> c)
	{
		WebApplicationContext ctx=WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		return ctx.getBean(name,c);
	}
	
	/**
	 * 获得登录用户 从HttpSession中获取或者从ThreadLocal中得到
	 * @author 胡礼波
	 * 2012-12-4 上午10:43:07
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Users getContextLoginUser()
	{
		try
		{
			Map<String,Object> map=ThreadLocalUtil.get(Map.class);
			if(map!=null)
			{
				if(map.containsKey(SysConst.USERID))
				{
					return (Users)map.get(SysConst.USERID);	
				}
			}
			Users user=(Users)getHttpServletRequest().getSession().getAttribute(SysConst.LOGIN_FLAG);
			return user;
		}
		catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 获取RequestAttributes对象
	 * @author 胡礼波-andy
	 * @2013-6-22下午3:15:09
	 * @return
	 */
	private static RequestAttributes getRequestAttributes()
	{
		RequestAttributes attri= RequestContextHolder.getRequestAttributes();
		return attri;
	}
	
	/**
	 * 获取ServletContext失败
	 * @author 胡礼波
	 * 2012-12-4 上午10:49:20
	 * @return
	 */
	public static ServletContext getServletContext()
	{
		try
		{
			ServletContext context=getHttpServletRequest().getServletContext();
			return context;
		}
		catch (Exception e) {
		}
		return null;
	}
	
	/**
	 * 获得HttpServletRequest
	 * @author 胡礼波
	 * 2012-12-4 上午10:45:11
	 */
	public static HttpServletRequest getHttpServletRequest()
	{
		try
		{
			HttpServletRequest request= ((ServletRequestAttributes)getRequestAttributes()).getRequest();
			return request;
		}catch (Exception e) {
		}
		return null;
	}
	
	
	/**
	 * 获取HttpServletResponse
	 * @author 胡礼波
	 * 2012-12-4 上午10:46:21
	 * @return
	 */
	public static HttpServletResponse getHttpServletResponse()
	{
		try
		{
			HttpServletResponse response= ((ServletWebRequest)getRequestAttributes()).getResponse();
			return response;
		}catch (Exception e) {
			logger.error("获取HttpServletResponse失败!");
		}
		return null;
	}
	
	/**
	 * 获得应用程序的根目录
	 * @author 胡礼波
	 * 2012-7-7 下午03:54:59
	 * @return
	 */
	public static String getAppPath()
	{
		String path=new File(StringUtil.class.getClassLoader().getResource("").getPath()).getParentFile().getParentFile().getPath();
		try {
			path=URLDecoder.decode(path,SysConst.ENCODEING_UTF8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	/**
	 * 获得WEB-INF下的目录结构
	 * @author 胡礼波
	 * 2012-7-7 下午03:48:14
	 * @param path  格式为"/XXX"
	 * @return
	 */
	public static String getWebInfPath(String path)
	{
		path=new File(StringUtil.class.getClassLoader().getResource("").getPath()).getParentFile().getPath()+path;
		try {
			path=URLDecoder.decode(path,SysConst.ENCODEING_UTF8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return path;
	}
	
	/**
	 * 获得ClassLoader目录
	 * @author 胡礼波
	 * 2012-7-7 下午03:52:57
	 * @param path XX/XX
	 * @return
	 */
	public static String getClassLoaderPath(String path)
	{
		path=StringUtil.class.getClassLoader().getResource(path).getPath();
		try {
			path=URLDecoder.decode(path,SysConst.ENCODEING_UTF8);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return path;
	}

	/**
	 * 获得客户端的IP Web请求来自HttpServletRquest<br/>
	 * Mina Socket请求来自ThreadLocal
	 * @author 胡礼波
	 * 2013-4-25 下午4:03:51
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getClientIp()
	{
		Map<String,Object> map=ThreadLocalUtil.get(Map.class);
		if(map!=null)
		{
			if(map.containsKey(SysConst.IP))
			{
				return String.valueOf(map.get(SysConst.IP));
			}
		}
		return getClientIp(getHttpServletRequest());
	}
	
	/**
	 * 获得客户端请求的IP
	 * @param request
	 * @author 胡礼波
	 * 2012-10-28 下午5:03:48
	 * @return
	 */
	public static String getClientIp(HttpServletRequest request)
	{
		if(request==null)
		{
			return "";
		}
		String unknow="unknown";
		String ip=request.getHeader("x-forwarded-for");
		if(ip == null || ip.length() == 0 || unknow.equalsIgnoreCase(ip)) {
			 ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || unknow.equalsIgnoreCase(ip)) {
			 ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || unknow.equalsIgnoreCase(ip)) {
				//Ngnix代理
			 ip = request.getHeader("X-Real-IP");
		}		
		if(ip == null || ip.length() == 0 || unknow.equalsIgnoreCase(ip)) {
		     ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	/**
	 * 判断请求是否来自一个站点 防盗链作用
	 * @author 胡礼波
	 * 2014-5-17 下午5:43:10
	 * @return
	 */
	public static boolean checkRequestIsSelfSite()
	{
		HttpServletRequest request=getHttpServletRequest();
		//传来的页面
		String refererTo=request.getHeader("referer");
		//防盗链
	   if(refererTo==null || refererTo.trim().equals("") || !refererTo.contains(request.getServerName())){
		   return false;  
        }
	   return true;
	}
	
	/**
	 * 获得Pager 翻页参数 形成Map对象返回
	 * Map中包含firstResult 开始记录 endResult结束记录
	 * @author 胡礼波
	 * 2014-6-12 下午3:33:30
	 * @return
	 */
	public static Map<String,Object> getThreadLocalPagerMap()
	{
		Map<String,Object> map=new HashMap<>(2);
		int pageNo=0,pageSize=0;
		try{
			Pager pager=Pager.getPager();
			if(pager!=null)
			{
				pageNo=pager.getPageNo();
				pageSize=pager.getPageSize();				
				int firstResult=(pageNo-1)*pageSize+1;
				map.put(SysConst.FIRSTRESULT,firstResult-1);
				map.put(SysConst.MAXRESULT,pageSize);
			}
			return map;
		}finally
		{
			Pager.remove();
		}
	}
}
