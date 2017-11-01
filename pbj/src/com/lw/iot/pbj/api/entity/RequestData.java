package com.lw.iot.pbj.api.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.alibaba.fastjson.JSONObject;
import com.lw.iot.pbj.common.json.JsonUtil;
import com.lw.iot.pbj.common.util.SecurityUtil;
import com.lw.iot.pbj.common.util.StringUtil;
import com.lw.iot.pbj.equipment.entity.PedometerData;

/**
 * 请求数据封装类
 * 该类主要封装HTTP请求的数据包格式
 * @author andy_hulibo
 * 2017年4月17日下午6:09:40
 */
public class RequestData implements Serializable{

	private static final long serialVersionUID = -472420361504328295L;
	
	/**
	 * 设备序列号
	 */
	private String serialNo;
	
	/**
	 * 设备版本
	 */
	private String version;
	
	/**
	 * 随机码
	 */
	private String nonceStr;
	
	/**
	 * 签名
	 */
	private String signature;
	
	/**
	 * 业务数据 一般是JSON格式
	 */
	private Object content;
	
	/**
	 * 经度
	 */
	private String longitude;
	
	/**
	 * 纬度
	 */
	private String latitude;
	
	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getNonceStr() {
		return nonceStr;
	}
	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public Object getContent() {
		return content;
	}
	public Object getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public void setContent(Object content) {
		this.content = content;
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

	public static void main(String[] args) {
		RequestData data=new RequestData();
		data.setNonceStr(StringUtil.getRandomString(11));
		data.setVersion("1.0");
		data.setLatitude("12.232");
		data.setLongitude("11.234234");
		
		PedometerData pd=new PedometerData();
		pd.setElectricity(11);
		pd.setSerialNo("111134dfgdf11");
		pd.setStep(122);
		pd.setTime(new Date());
		pd.setVersion(341);
		pd.setActTime(1234333);
		
		PedometerData pd1=new PedometerData();
		pd1.setElectricity(11);
		pd1.setSerialNo("11345431111");
		pd1.setStep(122);
		pd1.setTime(new Date());
		pd1.setVersion(1);
		pd1.setActTime(234);
		
		List<PedometerData> list=new ArrayList<PedometerData>();
		list.add(pd);list.add(pd1);
		data.setContent(list);
		data.setSerialNo("XXXXXXXXXXXXXXX3232XXXX");
		
		String signature = SecurityUtil.sha1(data.getSerialNo()+JsonUtil.toJsonStringFilterPropterForArray(data.getContent()).toJSONString()+data.getNonceStr()+data.getVersion()).toUpperCase(Locale.CHINA);
		System.out.println(signature.length());
		data.setSignature(signature);
		JSONObject json=JsonUtil.toJsonStringFilterPropter(data);
		System.out.println(json);
	}
}