package com.comm.server.netty;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import com.comm.server.entity.PackageData;
import com.comm.server.netty.handler.TcpHandler;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * TCp工具类
 * @author 胡礼波  andy_hulibo@163.com
 * @2017年8月14日 下午6:36:48
 */
public class TcpUtil {

	private static ChannelGroup channels=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

	/**
	 * 写数据
	 * @author 胡礼波  andy_hulibo@163.com
	 * @2017年8月15日 下午12:24:17
	 * @param channelId  设备序列号
	 * @param data
	 */
	public static void write(String channelId,PackageData data) throws Exception
	{
		Channel channel=null;
		int count=0;
		do
		{
			channel=getClient(channelId);
			if(channel==null || !channel.isActive())
			{
				count++;
				TimeUnit.SECONDS.sleep(5);				//尝试3次 等待设备连接上
			}else
			{
				break;
			}
		}while(count<3);
		
		if(channel==null || !channel.isActive())
		{
			throw new RuntimeException("TCP连接已经断开");
		}
		channel.writeAndFlush(data);
	}
	
	/**
	 * 客户端是否连接
	 * @author 胡礼波  andy_hulibo@163.com
	 * @2017年8月15日 下午5:56:37
	 * @param channelId
	 * @return
	 */
	public static boolean isConn(String channelId)
	{
		Channel channel =getClient(channelId);
		return channel==null?false:channel.isActive();
	}
	
	/**
	 * 获得客户端连接
	 * @author 胡礼波  andy_hulibo@163.com
	 * @2017年8月14日 下午6:37:30
	 * @param channelId
	 * @return
	 */
	public static Channel getClient(String channelId)
	{
		for (Channel channel : channels) {
			if(channelId.equals(channel.attr(TcpHandler.KEY_SERIAL).get()))
			{
				return channel;
			}
		}
		return null;
	}
	
	/**
	 * 增加一个连接客户端
	 * @author 胡礼波  andy_hulibo@163.com
	 * @2017年8月14日 下午6:35:00
	 */
	public static void addClient(Channel channel)
	{
		String channelId=channel.attr(TcpHandler.KEY_SERIAL).get();
		Iterator<Channel> it=channels.iterator();
		while(it.hasNext())
		{
			Channel c=it.next();
			String id=c.attr(TcpHandler.KEY_SERIAL).get();
			if(id==null || channelId==null)
			{
				continue;
			}
			if(channelId.equals(id))
			{
				it.remove();
				c.close();				//关闭已有的连接
				break;
			}	
		}
		channels.add(channel);
	}
	
	/**
	 * 移除一个连接客户端
	 * @author 胡礼波  andy_hulibo@163.com
	 * @2017年8月14日 下午6:35:53
	 * @param channel
	 */
	public static void removeClient(Channel channel)
	{
		channels.remove(channel);
	}
}
