package com.bfly.trade.members.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户类
 * 
 * @author 胡礼波 2012-4-24 下午11:15:51
 */
public class Members implements Serializable {

	/**
	 * @author 胡礼波 2012-4-24 下午11:15:57
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	/**
	 * 微信号
	 */
	private String cusCode;

	/**
	 * 手机号码
	 */
	private String phone;
	
	/**
	 * 账号状态
	 */
	private boolean enable;

	/**
	 * 注册IP
	 */
	private String registeIp; 
	
	/**
	 * 注册时间
	 */
	private Date registeTime;

	/**
	 * 昵称
	 */
	private String nickName;
	
	/**
	 * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
	 */
	private int sex;
	
	/**
	 * 用户所在省份
	 */
	private String province;
	
	/**
	 * 用户所在城市
	 */
	private String city;
	
	/**
	 * 用户所在国家
	 */
	private String country;
	
	/**
	 * 用户头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像），用户没有头像时该项为空。若用户更换头像，原有头像URL将失效。
	 */
	private String headImgUrl;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getRegisteTime() {
		return registeTime;
	}

	public void setRegisteTime(Date registeTime) {
		this.registeTime = registeTime;
	}

	public boolean getEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getRegisteIp() {
		return registeIp;
	}

	public void setRegisteIp(String registeIp) {
		this.registeIp = registeIp;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCusCode() {
		return cusCode;
	}

	public void setCusCode(String cusCode) {
		this.cusCode = cusCode;
	}
}
