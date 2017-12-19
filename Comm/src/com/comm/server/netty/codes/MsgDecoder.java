package com.comm.server.netty.codes;

import java.util.List;

import com.comm.server.entity.PackageData;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * 消息解码
 * @author 胡礼波  andy_hulibo@163.com
 * @2017年8月14日 下午5:03:26
 */
public class MsgDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if(in.readableBytes()<4)
		{
			return;
		}
		in.markReaderIndex();
		int length=in.readInt();			//获取内容长度
		if(length<0)
		{
			ctx.close();
			return;
		}
		
		if(in.readableBytes()<12+4+36+length)
		{
			in.resetReaderIndex();
			return;
		}
		byte[] b1=new byte[12];
		in.readBytes(b1);
		String messageId=new String(b1,0,12);

		
		byte[] b2=new byte[4];
		in.readBytes(b2);
		String cmd=new String(b2,0,4);
		
		
		byte[] b3=new byte[36];
		in.readBytes(b3);
		String serialNo=new String(b3,0,36);
		
		
		byte[] b4=new byte[length];
		in.readBytes(b4);
		String content=new String(b4,0,length);
		
		PackageData pkgData = new PackageData();
		pkgData.setCmd(cmd);
		pkgData.setSerialNo(serialNo);
		pkgData.setMessageId(messageId);
		pkgData.setContent(content);
		out.add(pkgData);
	}

}
