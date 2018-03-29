package com.bfly.industry.members.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 商户信息类
 * @author andy_hulibo@163.com
 * @2018年1月11日 下午3:15:08
 */
public class SellerInfo implements Serializable{

	/**
	 * @author andy_hulibo@163.com
	 * @2018年1月11日 下午3:15:29
	 */
	private static final long serialVersionUID = 8654746712308778299L;

	private int id;
	
	/**
	 * 会员ID
	 */
	private int memberId;
	
	/**
	 * 商户名称
	 */
	private String name;
	
	/**
	 * 商户手机号码
	 */
	private String phone;
	
	/**
	 * 商户地址经度
	 */
	private String longitude;
	
	/**
	 * 商户地址纬度
	 */
	private String latitude;
	
	/**
	 * 商户地址
	 */
	private String address;
	
	/**
	 * 营业时间 如:10:00-23:00
	 */
	private String shopHours;
	
	/**
	 * 商户logo
	 */
	private String logo;
	
	/**
	 * 商户类型
	 */
	private int type;
	
	/**
	 * 商户状态
	 */
	private boolean enable;
	
	/**
	 * 是否推荐
	 */
	private boolean recommend;
	
	/**
	 * 商户提供设施 比如:wifi,停车位,微信支付,支付宝支付等设施服务 用ID拼接起来
	 */
	private String facility;
	
	/**
	 * 商户入住时间
	 */
	private Date registeTime;
	
	/**
	 * 商户简介
	 */
	private String remark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public boolean isRecommend() {
		return recommend;
	}

	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getShopHours() {
		return shopHours;
	}

	public void setShopHours(String shopHours) {
		this.shopHours = shopHours;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	/**
	 * 状态名称
	 * @author andy_hulibo@163.com
	 * @2018年1月12日 上午10:46:35
	 * @return
	 */
	public String getEnableName()
	{
		return isEnable()?"正常":"已禁用";
	}
	
	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getFacility() {
		return facility;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getRegisteTime() {
		return registeTime;
	}

	public void setRegisteTime(Date registeTime) {
		this.registeTime = registeTime;
	}
}
