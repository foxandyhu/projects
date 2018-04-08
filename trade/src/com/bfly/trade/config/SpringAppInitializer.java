package com.bfly.trade.config;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.util.IntrospectorCleanupListener;

import com.bfly.trade.util.SysConst;

/**
 * Spring应用启动类
 * @author andy_hulibo@163.com
 * @2018年3月30日 下午5:47:21
 */
public class SpringAppInitializer implements WebApplicationInitializer {

	private Logger logger=LoggerFactory.getLogger(SpringAppInitializer.class);
			
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding(SysConst.ENCODEING_UTF8);
		servletContext.addFilter("CharactEncoding", encodingFilter).addMappingForUrlPatterns(null,true,"/*");
		
		AnnotationConfigWebApplicationContext rootContext =new AnnotationConfigWebApplicationContext();
		rootContext.register(SpringConfig.class);
		
		servletContext.addListener(new ContextLoaderListener(rootContext));
		servletContext.addListener(new IntrospectorCleanupListener());
		servletContext.addListener(new RequestContextListener());

		AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
		dispatcherContext.register(SpringMvcConfig.class);

		ServletRegistration.Dynamic dispatcher =servletContext.addServlet("trade", new DispatcherServlet(dispatcherContext));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
		
	}

	@PostConstruct
	public void init()
	{
		logger.info("the spring App Initializer is initialized");
	}
}
