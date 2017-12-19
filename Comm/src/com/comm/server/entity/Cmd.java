package com.comm.server.entity;

/**
 * 命令
 * @author andy_hulibo@163.com
 * @2017年11月30日 下午4:09:37
 */
public class Cmd {

	//开门
	public static final String CMD_OPEN ="1000";
	
	//心跳
	public static final String CMD_HEARTBEAT="1011";
	
	//断电
	public static final String CMD_POWER_CHANGE="1012";
	
	//温度
	public static final String CMD_TEMPERATURE="1013";
	
	//查询门状态
	public static final String CMD_QUERY_OPEN="2000";
}
