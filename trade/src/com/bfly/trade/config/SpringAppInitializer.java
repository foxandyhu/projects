//package com.bfly.trade.config;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRegistration;
//
//import org.springframework.web.WebApplicationInitializer;
//import org.springframework.web.context.ContextLoaderListener;
//import org.springframework.web.context.request.RequestContextListener;
//import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
//import org.springframework.web.context.support.XmlWebApplicationContext;
//import org.springframework.web.filter.CharacterEncodingFilter;
//import org.springframework.web.servlet.DispatcherServlet;
//import org.springframework.web.util.IntrospectorCleanupListener;
//
//import com.bfly.trade.util.SysConst;
//
///**
// * Spring应用启动类
// * @author andy_hulibo@163.com
// * @2018年3月30日 下午5:47:21
// */
//public class SpringAppInitializer implements WebApplicationInitializer {
//
//	@Override
//	public void onStartup(ServletContext servletContext) throws ServletException {
//		
//		AnnotationConfigWebApplicationContext rootContext =new AnnotationConfigWebApplicationContext();
//		rootContext.register(AppConfig.class);
//		
//		servletContext.addListener(new ContextLoaderListener(rootContext));
//		servletContext.addListener(new IntrospectorCleanupListener());
//		servletContext.addListener(new RequestContextListener());
//		
////		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
////		encodingFilter.setEncoding(SysConst.ENCODEING_UTF8);
////		servletContext.addFilter("CharactEncoding", encodingFilter);
//
//		AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
//		dispatcherContext.register(SpringMvcConfig.class);
//
//		ServletRegistration.Dynamic dispatcher =servletContext.addServlet("trade", new DispatcherServlet(dispatcherContext));
//		dispatcher.setLoadOnStartup(1);
//		dispatcher.addMapping("/");
//		
//	}
//
//}
