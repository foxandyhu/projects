package com.bfly.trade.system.entity;

import java.io.Serializable;

/**
 * 系统角色
 * @author 胡礼波-Andy
 * @2016年9月23日下午5:24:03
 */
public class SysRole implements Serializable,Comparable<SysRole>{

	/**
	 * @author 胡礼波-Andy
	 * @2015年12月8日下午3:39:39
	 */
	private static final long serialVersionUID = -2304871013539495763L;

	private int id;
	private String name;					//角色名称
	private int seq;						//顺序
	private boolean enable; 				//角色是否启用
	private String remark;					//备注
	
	public int getId() {
		return id;
	}

	public String getDisabledName()
	{
		return isEnable()?"启用":"禁用";
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	@Override
	public int compareTo(SysRole role) {
		return Integer.valueOf(this.getSeq()).compareTo(Integer.valueOf(role.getSeq()));
	}
}
