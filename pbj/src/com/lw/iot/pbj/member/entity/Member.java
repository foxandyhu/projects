package com.lw.iot.pbj.member.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员信息
 * 
 * @author 胡礼波-Andy
 * @2016年9月5日下午5:00:05
 */
public class Member implements Serializable {

	/**
	 * @author 胡礼波-Andy
	 * @2016年9月5日下午5:00:19
	 */
	private static final long serialVersionUID = 3267663492709936040L;
	private int id;
	
	/**
	 * 手机号
	 */
	private String phone; 
	
	/**
	 * 是否可用
	 */
	private boolean enable; 
	
	/**
	 * 注册时间
	 */
	private Date registTime; 

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Date getRegistTime() {
		return registTime;
	}

	public void setRegistTime(Date registTime) {
		this.registTime = registTime;
	}
}
