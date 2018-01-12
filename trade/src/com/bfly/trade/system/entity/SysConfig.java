package com.bfly.trade.system.entity;

import java.io.Serializable;

/**
 * 系统配置类
 * @author 胡礼波-Andy
 * @2015年1月23日下午3:21:42
 */
public class SysConfig implements Serializable{

	/**
	 * @author 胡礼波-Andy
	 * @2015年1月23日下午3:21:51
	 */
	private static final long serialVersionUID = -8122970883157971015L;

	private int id;
	
	private String appName;				//系统名称

	private String copyRight;			//系统版权
	
	private String address;				//企业地址
	
	private String phone;				//联系电话
	
	private String record;				//备案号

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCopyRight() {
		return copyRight;
	}

	public void setCopyRight(String copyRight) {
		this.copyRight = copyRight;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
