package com.bfly.industry.enums;

/**
 * 设施服务
 * @author andy_hulibo@163.com
 * @2018年1月12日 上午11:06:13
 */
public enum Facility {

	WIFI(1),
	PARKING(2),
	ALIPAY(3),
	TENCENTPAY(4);
	
	private int id;
	
	private Facility(int id)
	{
		this.id=id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
}
