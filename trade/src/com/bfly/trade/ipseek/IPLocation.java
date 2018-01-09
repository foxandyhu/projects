package com.bfly.trade.ipseek;

/**
 * 封装ip相关信息，目前只有两个字段，ip所在的国家和地区
 * @author 胡礼波
 * 2012-6-1 下午12:23:04
 */
public class IPLocation {
	private String country = "";
	private String area = "";

	public IPLocation() {}
	
	public IPLocation(String country, String area){
		this.country = country;
		this.area = area;
	}

	public IPLocation getCopy() {
		IPLocation ret = new IPLocation();
		ret.country = country;
		ret.area = area;
		return ret;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getArea() {
		if("保留地址用于本地回送".equals(area))
		{
			return "局域网";
		}
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
}