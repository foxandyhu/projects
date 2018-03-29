package com.lw.iot.pbj.core.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.lw.iot.pbj.common.util.ContextUtil;
import com.lw.iot.pbj.system.entity.SysConfig;
import com.lw.iot.pbj.system.service.ISysConfigService;

/**
 * 系统加载时监听器
 * @author 胡礼波
 * 2012-11-21 上午11:11:52
 */
@WebListener
public class SystemLoaderListener implements ServletContextListener {

	/**
	 * 系统销毁时调用
	 * @author 胡礼波
	 * 2012-11-21 上午11:13:35
	 * @param event
	 */
	@Override
	public void contextDestroyed(ServletContextEvent event) 
	{
	}
	
	/**
	 * 系统启动时调用
	 * @author 胡礼波
	 * 2012-11-21 上午11:13:46
	 * @param event
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		loadSysConfig(event.getServletContext());
	}
	
	/**
	 * 加载系统配置
	 * @author 胡礼波-Andy
	 * @2015年7月27日下午12:07:27
	 * @param ctx
	 */
	public void loadSysConfig(ServletContext ctx)
	{
		try
		{
			ISysConfigService sysConfigService=ContextUtil.getWebContextBean(ISysConfigService.class,ctx);
			SysConfig config= sysConfigService.getSysConfig();
			if(config!=null)
			{
				ctx.setAttribute("sysConfig",config);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
