package com.lw.iot.pbj.chicken.entity;

import java.io.Serializable;
import java.util.Date;

import com.lw.iot.pbj.common.enums.SysEnum;

/**
 * 鸡
 * @author sunships
 * @date 2018年1月10日下午2:57:26
 */
public class Chicken implements Serializable{
	
	/**
	 * @author sunships
	 * @date 2018年1月10日下午2:57:17
	 */
	private static final long serialVersionUID = -1737572465120537594L;
	private int id;
	/**
	 * 鸡只编号（设备编号）
	 */
	private String serialNo;
	/**
	 * 品种
	 */
	private String breeds;
	/**
	 * 鸡只状态1、饲养中2、出栏中
	 */
	private int status;
	/**
	 * 脚环佩戴时间
	 */
	private Date wearTime;
	/**
	 * 品种特点
	 */
	private String remark;
	/**
	 * 未佩戴日龄
	 */
	private int birthDays;
	/**
	 * 养殖场名称
	 */
	private String farm;
	/**
	 * 出栏日
	 */
	private Date sellTime;
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
	public String getBreeds() {
		return breeds;
	}
	public void setBreeds(String breeds) {
		this.breeds = breeds;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getWearTime() {
		return wearTime;
	}
	public void setWearTime(Date wearTime) {
		this.wearTime = wearTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getBirthDays() {
		return birthDays;
	}
	public void setBirthDays(int birthDays) {
		this.birthDays = birthDays;
	}
	public String getFarm() {
		return farm;
	}
	public void setFarm(String farm) {
		this.farm = farm;
	}
	public Date getSellTime() {
		return sellTime;
	}
	public void setSellTime(Date sellTime) {
		this.sellTime = sellTime;
	}
	public String getStatusStr(){
		return SysEnum.ChickenStatus.getDesc(this.status).getDesc();
	}
	public long getWearDays() {
		if(this.status==SysEnum.ChickenStatus.FEEDING.getKey()){
			return (new Date().getTime()-this.wearTime.getTime())/(24*60*60*1000)+1;    
		}else{
			return (this.getSellTime().getTime()-this.wearTime.getTime())/(24*60*60*1000)+1;    
		}
	}
	public long getDays(){
		return getWearDays()+this.birthDays;
	}
}
