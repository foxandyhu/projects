package com.bfly.trade.enums;

/**
 * 商户分类
 * @author andy_hulibo@163.com
 * @2018年1月12日 上午11:29:25
 */
public enum SellerType {

	MEISHI(1000),
	YULEXIUXIAN(1001),
	JIUDIANZHUSU(1002),
	YUNDONGJIANSHEN(1003),
	SHISHANGDAREN(1004),
	MUYINGQINZI(1005),
	XUEXIPEIXUN(1006),
	LVYOUCHUXING(1007),
	SHENGHUOFUWU(1008);
	
	private int id;
	private SellerType(int id)
	{
		this.id=id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
