package com.lw.iot.pbj.equipment.entity;

import java.io.Serializable;

/**
 * 计步器设备实体类
 * @author 胡礼波  andy_hulibo@163.com
 * @2017年10月30日 上午11:36:22
 */
public class Pedometer implements Serializable{

	/**
	 * @author 胡礼波  andy_hulibo@163.com
	 * @2017年10月30日 上午11:36:33
	 */
	private static final long serialVersionUID = -112937391692779758L;

	private int id;
	
	/**
	 * 设备序列号
	 */
	private String serialNo;		
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
}
