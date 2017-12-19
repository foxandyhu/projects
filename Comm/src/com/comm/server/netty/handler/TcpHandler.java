package com.comm.server.netty.handler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.comm.server.entity.Cmd;
import com.comm.server.entity.PackageData;
import com.comm.server.equipment.EquipmentService;
import com.comm.server.netty.TcpUtil;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;

/**
 * TCP消息处理类
 * @author 胡礼波  andy_hulibo@163.com
 * @2017年8月14日 下午5:03:06
 */
@Component("TcpHandler")
@Scope("prototype")
@Sharable
public class TcpHandler extends ChannelInboundHandlerAdapter{

	private Logger logger=Logger.getLogger(TcpHandler.class);
	public static AttributeKey<String> KEY_SERIAL = AttributeKey.valueOf("serialNo");
	@Autowired
	private EquipmentService equipmentService;
	
	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception{
		if(evt instanceof IdleStateEvent)
		{
			IdleStateEvent event=(IdleStateEvent)evt;
			if(event.state().equals(IdleState.READER_IDLE))			//30秒写空闲则认为掉线
			{
				ctx.close();
				logger.warn("no receive heartbeat data close the connect "+ctx.channel().attr(KEY_SERIAL).get());
			}else if(event.state().equals(IdleState.WRITER_IDLE))
			{
			}else if(event.state().equals(IdleState.ALL_IDLE))
			{
			}
		}
	}
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		TcpUtil.addClient(ctx.channel());
		logger.info("a client come in "+ctx.channel().remoteAddress().toString());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		TcpUtil.removeClient(ctx.channel());
		logger.info("a client is closed "+ctx.channel().remoteAddress().toString());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		PackageData pkgData=(PackageData)msg;
		
		PackageData pd=new PackageData();
		pd.setMessageId(pkgData.getMessageId());
		pd.setContent(pkgData.getContent());
		pd.setSerialNo(pkgData.getSerialNo());
		pd.setCmd(pkgData.getCmd());
		
		equipmentService.doCmd(pd);
		
		doCmd(ctx,pkgData);	
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.error(cause.getMessage(),cause);
		ctx.close();
	}

	/**
	 * 处理命令
	 * @author 胡礼波  andy_hulibo@163.com
	 * @2017年8月14日 下午6:23:26
	 * @param cmd
	 */
	private void doCmd(ChannelHandlerContext ctx,PackageData data)
	{
		ctx.channel().attr(KEY_SERIAL).set(data.getSerialNo());
		
		switch(data.getCmd())
		{
			case Cmd.CMD_HEARTBEAT:
				data.setContent("");
				ctx.writeAndFlush(data);
				break;
			case Cmd.CMD_OPEN:
				break;
			case Cmd.CMD_POWER_CHANGE:
				data.setContent("");
				ctx.writeAndFlush(data);
				break;
			case Cmd.CMD_TEMPERATURE:
				data.setContent("");
				ctx.writeAndFlush(data);
				break;
			default:
				ctx.close();
				return;
		}
		
	}
}
