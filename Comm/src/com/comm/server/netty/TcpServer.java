package com.comm.server.netty;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.comm.server.netty.codes.MsgDecoder;
import com.comm.server.netty.codes.MsgEncoder;
import com.comm.server.netty.handler.TcpHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * Netty服务端
 * @author 胡礼波  andy_hulibo@163.com
 * @2017年8月14日 下午4:49:48
 */
public class TcpServer {

	private Logger logger=Logger.getLogger(TcpServer.class);
	
	private int port;
	private String ip;
	private String remoteIp;			//远程IP 主要针对云主机 例如 腾讯云 只允许绑定内网IP
	private int backLog;
	private EventLoopGroup bossGroup=new NioEventLoopGroup();
	private EventLoopGroup workerGroup=new NioEventLoopGroup();
	private ServerBootstrap boot=null;
	private static ChannelFutureListener futureListener;
	@Autowired
	private TcpHandler handler;
	
	/**
	 * 初始化服务
	 * @author 胡礼波  andy_hulibo@163.com
	 * @2017年8月14日 下午4:57:10
	 */
	private void init()
	{
		try
		{
			boot=new ServerBootstrap();
			boot.group(bossGroup,workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG,getBackLog())
			.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new MsgEncoder());
					ch.pipeline().addLast(new MsgDecoder());
					ch.pipeline().addLast(new IdleStateHandler(30,30,30,TimeUnit.SECONDS));
					ch.pipeline().addLast(handler);
				}
			});
			futureListener=new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if(future.isSuccess())
					{
						logger.info("TCP服务开启成功!");
					}else
					{
						future.channel().eventLoop().schedule(new Runnable() {
							@Override
							public void run() {
								bindPort();
							}
						},2,TimeUnit.SECONDS);
					}
				}
			};
		}catch(Exception e)
		{
			logger.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 绑定端口号
	 * @author 胡礼波  andy_hulibo@163.com
	 * @throws Exception 
	 * @2017年8月14日 下午5:12:45
	 */
	private void bindPort()
	{
		ChannelFuture future;
		try
		{
			future = boot.bind(getIp(),getPort());
			future.addListener(futureListener);
			future.sync();
		} catch (Exception e) {
			logger.error("TCP 服务开启异常"+e.getMessage());
		}
	}
	
	/**
	 * 启动服务
	 * @author 胡礼波  andy_hulibo@163.com
	 * @2017年8月14日 下午5:18:36
	 */
	public void start()
	{
		init();
		bindPort();
	}
	
	/**
	 * 停止服务
	 * @author 胡礼波  andy_hulibo@163.com
	 * @2017年8月14日 下午4:59:40
	 */
	public void stop()
	{
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
		logger.info("关闭TCP端口成功!");
	}
	
	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getBackLog() {
		return backLog;
	}

	public void setBackLog(int backLog) {
		this.backLog = backLog;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRemoteIp() {
		return remoteIp;
	}

	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
}
