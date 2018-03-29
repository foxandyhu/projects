package com.bfly.trade.members.entity;

import java.io.Serializable;

/**
 * 商户图片
 * @author andy_hulibo@163.com
 * @2018年3月27日 上午11:46:13
 @SuppressWarnings("serial")
*/
public class SellerPics implements Serializable {

	/**
	 * @author andy_hulibo@163.com
	 * @2018年3月27日 上午11:47:37
	 */
	private static final long serialVersionUID = -7447229274140967766L;

	private int id;
	
	/**
	 * 商户ID
	 */
	private int sellerId;
	
	/**
	 * 图片地址
	 */
	private String url;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSellerId() {
		return sellerId;
	}

	public void setSellerId(int sellerId) {
		this.sellerId = sellerId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
