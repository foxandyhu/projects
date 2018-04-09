package com.bfly.industry.enums;

/**
 * 商户分类
 * @author andy_hulibo@163.com
 * @2018年1月12日 上午11:29:25
 */
public enum SellerType {

	MEISHI(1000,"餐饮美食"),
	YULEXIUXIAN(1001,"生活服务"),
	JIUDIANZHUSU(1002,"酒店住宿"),
	MEIRONGBAOJIAN(1003,"美容保健"),
	CHELIANGFUWU(1004,"车辆服务"),
	MUYINGQINZI(1005,"母婴亲子"),
	XUEXIPEIXUN(1006,"学习培训"),
	LVYOUCHUXING(1007,"旅游出行");
	
	private int id;
	private String name;
	
	public static SellerType getSellerType(int id)
	{
		for (SellerType type:values()) {
			if(type.getId()==id)
			{
				return type;
			}
		}
		return null;
	}
	
	private SellerType(int id,String name)
	{
		this.id=id;
		this.name=name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
