package com.bfly.industry.exception;

/**
 * 检验是否需要登录异常
 * @author andy_hulibo@163.com
 * @2018年1月10日 上午10:48:59
 */
public class NeedLoginException extends Exception {

	/**
	 * @author andy_hulibo@163.com
	 * @2018年1月10日 上午10:43:43
	 */
	private static final long serialVersionUID = -6512140406332943452L;

	public NeedLoginException() {
		super("need login!");
	}

	public NeedLoginException(String message) {
		super(message);
	}
}
