package com.bfly.trade.system.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单
 * @author 胡礼波-Andy
 * @2016年9月23日下午5:25:24
 */
public class SysMenu implements Serializable,Comparable<SysMenu>{

	/**
	 * @author 胡礼波-Andy
	 * @2016年9月23日下午5:25:50
	 */
	private static final long serialVersionUID = -2896321290631178356L;
	
	public static final int SYS_MENU=1;			//系统菜单
	public static final int FUN_MENU=2;			//功能菜单

	private int id;
	
	private String name;						//菜单名
	
	private int type;							//菜单类型
	
	private int seq = 1;						//菜单顺序
	
	private String url;							//菜单URL
	
	private int parentId;						//父菜单
	
	private boolean enable;						//菜单是否启用
	
	private String remark;						//备注
	
	private List<SysMenu> children;				//子菜单

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public List<SysMenu> getChildren() {
		return children;
	}

	public void setChildren(List<SysMenu> children) {
		this.children = children;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	@Override
	public int compareTo(SysMenu m) {
		return Integer.valueOf(this.getSeq()).compareTo(Integer.valueOf(m.getSeq()));
	}	
}
