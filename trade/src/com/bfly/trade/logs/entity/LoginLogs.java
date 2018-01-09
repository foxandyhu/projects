package com.bfly.trade.logs.entity;

import java.io.Serializable;
import java.util.Date;

import com.bfly.trade.ipseek.IPLocation;
import com.bfly.trade.ipseek.IPSeeker;

/**
 * 用户登录日志
 * @author 胡礼波 2012-4-24 下午11:15:51
 */
public class LoginLogs implements Serializable {

	/**
	 * @author 胡礼波 2012-4-24 下午11:15:57
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int MEMBER_LOGIN=1;			//会员登录
	public static final int USERS_LOGIN=2;			//系统管理员登录
	public static final int RESTAURATEUR_LOGIN=3;	//商户登录

	private int id;

	private String userName;	// 账号
	private int type;			// 账号类型---管理员登录 会员登录

	private Date time;			//登录时间

	private String ip;			//登录ip
	
	private boolean login;		//退出还是登录

	public LoginLogs(){}
	public LoginLogs(String userName,String ip,int type,boolean login)
	{
		this.userName=userName;
		this.ip=ip;
		this.type=type;
		this.time=new Date();
		this.login=login;
	}
	
	/**
	 * 类型名称
	 * @author 胡礼波-Andy
	 * @2016年9月23日下午5:11:56
	 * @return
	 */
	public String getTypeName()
	{
		switch(getType())
		{
			case 1:
				return "会员";
			case 2:
				return "管理员";
			case 3:
				return "商户";
			default:
					return "未知类型";
		}
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isLogin() {
		return login;
	}
	public void setLogin(boolean login) {
		this.login = login;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getIpArea()
	{
		IPLocation loca=IPSeeker.getInstance().getIPLocation(getIp());
		return loca.getCountry();
	}
}
