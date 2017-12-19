package com.comm.server.entity;

import java.io.Serializable;

import com.comm.server.util.StringUtil;

/**
 * 响应客户端数据
 * @author andy_hulibo@163.com
 * @2017年11月30日 下午3:52:21
 */
public class ResponseData implements Serializable{

	public static final String SUCCESS="SUCCESS",FAIL="FAIL";
	
	/**
	 * @author andy_hulibo@163.com
	 * @2017年11月30日 下午3:54:52
	 */
	private static final long serialVersionUID = -8488298101176088836L;

	//设备序列号
	private String serialno;
	
	//消息ID
	private String messageid;
	
	//指令
	private String cmd;
	
	//内容
	private String content;
	
	//状态 SUCCESS FAIL
	private String status;
	
	
	public static ResponseData getSuccessData(String cmd,String content,String serialNo)
	{
		ResponseData data=new ResponseData();
		data.setCmd(cmd);
		data.setContent(content);
		data.setSerialno(serialNo);
		data.setMessageid(StringUtil.getRandomString(12));
		data.setStatus(ResponseData.SUCCESS);
		return data;
	}
	
	public static ResponseData getFailData(String cmd,String content,String serialNo)
	{
		ResponseData data=new ResponseData();
		data.setCmd(cmd);
		data.setContent(content);
		data.setSerialno(serialNo);
		data.setMessageid(StringUtil.getRandomString(12));
		data.setStatus(ResponseData.FAIL);
		return data;
	}
	
	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public String getMessageid() {
		return messageid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
