package com.lw.iot.pbj.equipment.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 计步器读取设备 与服务器通讯的主机设备
 * @author andy_hulibo@163.com
 * @2017年10月31日 下午2:28:21
 */
public class PedometerReader implements Serializable {

	/**
	 * @author andy_hulibo@163.com
	 * @2017年10月31日 下午2:28:50
	 */
	private static final long serialVersionUID = -1923903634618865875L;

	private int id;
	
	/**
	 * 设备序列号
	 */
	private String serialNo;
	
	/**
	 * 设备安装地址
	 */
	private String address;
	
	/**
	 * 是否在线
	 */
	private boolean online;
	
	/**
	 * 最后通讯时间
	 */
	private Date lastComm;
	
	/**
	 * 是否启用
	 */
	private boolean enable;
	
	/**
	 * 备注
	 */
	private String remark;

	
	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public Date getLastComm() {
		return lastComm;
	}

	public void setLastComm(Date lastComm) {
		this.lastComm = lastComm;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
}
