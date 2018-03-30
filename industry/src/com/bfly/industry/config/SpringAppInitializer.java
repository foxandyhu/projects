package com.bfly.industry.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * Spring应用启动类
 * @author andy_hulibo@163.com
 * @2018年3月30日 下午5:47:21
 */
public class SpringAppInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		XmlWebApplicationContext appContext = new XmlWebApplicationContext();
		appContext.setConfigLocation("classpath:applicationContext-mvc.xml");
		ServletRegistration.Dynamic dispatcher =servletContext.addServlet("industry", new DispatcherServlet(appContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}

}
