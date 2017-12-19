package com.comm.server.entity;

import java.io.Serializable;

/**
 * 封装数据
 * @2017年11月14日 下午12:20:26
 */
public class PackageData implements Serializable{

	/**
	 * @2017年11月14日 下午12:20:34
	 */
	private static final long serialVersionUID = 1L;
	
	private String messageId;
	private String cmd;
	private String serialNo;
	private String content;
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
