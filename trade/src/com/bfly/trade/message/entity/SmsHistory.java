package com.bfly.trade.message.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信记录实体类
 * @author 胡礼波-Andy
 * @2016年8月30日上午10:13:14
 */
public class SmsHistory implements Serializable {

	/**
	 * @author 胡礼波-Andy
	 * @2016年8月30日上午10:13:26
	 */
	private static final long serialVersionUID = -7657651148051900818L;
	
	public final static int WAIT=0;			//等到发送
	public final static int FAILED=1;			//发送失败
	public final static int SUCCESS=2;		//发送成功

	private int id;
	private String phone;				//手机号码
	private String content;				//短信内容
	private Date time;					//发送时间
	private int status;					//短信状态
	private int total;					//发送次数
	
	/**
	 * 状态名称
	 * @author 胡礼波-Andy
	 * @2017年1月9日上午11:10:11
	 * @return
	 */
	public String getStatusName()
	{
		switch(getStatus())
		{
			case WAIT:
				return "待发送";
			case FAILED:
				return "发送失败";
			case SUCCESS:
				return "发送成功";
			default:
					return "未知状态";
		}
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
}
