package com.bfly.industry.exception;

/**
 * 没有权限访问资源异常
 * @author andy_hulibo@163.com
 * @2018年1月10日 上午10:43:33
 */
public class NoPermissionToAccessResourceException extends Exception {

	/**
	 * @author andy_hulibo@163.com
	 * @2018年1月10日 上午10:43:43
	 */
	private static final long serialVersionUID = -6512140406332943452L;

	public NoPermissionToAccessResourceException() {
		super("No Permission to access resources");
	}

	public NoPermissionToAccessResourceException(String message) {
		super(message);
	}
}
