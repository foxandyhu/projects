package com.lw.iot.pbj.users.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 * @author 胡礼波-Andy
 * @2014年11月10日上午9:38:53
 *
 */
public class Users implements Serializable{

	/**
	 * @author 胡礼波-Andy
	 * @2014年11月10日上午9:39:06
	 * 
	 */
	private static final long serialVersionUID = 2985559170913083284L;

	private int id;
	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 用户名
	 */
	private String userName;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 帐号是否启用
	 */
	private boolean enable;
	
	/**
	 * 密钥盐
	 */
	private String salt;
	
	/**
	 * 注册时间
	 */
	private Date registeDate;
	
	/**
	 * 注册IP
	 */
	private String registIp;
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Date getRegisteDate() {
		return registeDate;
	}

	public void setRegisteDate(Date registeDate) {
		this.registeDate = registeDate;
	}

	public String getRegistIp() {
		return registIp;
	}

	public void setRegistIp(String registIp) {
		this.registIp = registIp;
	}

	public Date getRegistDate() {
		return registeDate;
	}

	public void setRegistDate(Date registeDate) {
		this.registeDate = registeDate;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
