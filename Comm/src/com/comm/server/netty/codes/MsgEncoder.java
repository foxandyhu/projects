package com.comm.server.netty.codes;

import com.comm.server.entity.PackageData;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 消息编码
 * @author 胡礼波  andy_hulibo@163.com
 * @2017年8月14日 下午5:03:33
 */
public class MsgEncoder extends MessageToByteEncoder<PackageData> {

	@Override
	protected void encode(ChannelHandlerContext ctx, PackageData data, ByteBuf out) throws Exception 
	{
		data.toString();
		String dataStr=data.getContent();
		
		byte bytes[]=dataStr.getBytes("UTF-8");
		int length=bytes.length;
		
		out.writeInt(length);								//消息长度
		out.writeBytes(data.getMessageId().getBytes());		//消息ID
		out.writeBytes(data.getCmd().getBytes());			//消息指令
		out.writeBytes(data.getSerialNo().getBytes());		//设备编号
		out.writeBytes(bytes, 0,length);					//消息内容
	}
}
