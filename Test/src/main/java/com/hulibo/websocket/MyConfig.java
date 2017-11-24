package com.hulibo.websocket;

import java.util.Set;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerApplicationConfig;
import javax.websocket.server.ServerEndpointConfig;


public class MyConfig implements ServerApplicationConfig {

	public Set<ServerEndpointConfig> getEndpointConfigs(
			Set<Class<? extends Endpoint>> endpointClasses) {
		System.out.println("******************************");
		return null;
	}

	public Set<Class<?>> getAnnotatedEndpointClasses(Set<Class<?>> scanned) {
		for (Class<?> class1 : scanned) {
			System.out.println(class1.getSimpleName());
		}
		System.out.println(">>>>>>>>>>>>>>>>>>>>>");
		return scanned;
	}
}
