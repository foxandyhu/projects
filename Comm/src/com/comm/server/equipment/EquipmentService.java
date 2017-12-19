package com.comm.server.equipment;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.comm.server.entity.Cmd;
import com.comm.server.entity.PackageData;
import com.comm.server.netty.TcpUtil;
import com.comm.server.util.JsonUtil;
import com.comm.server.util.NetUtil;
import com.comm.server.util.NetUtil.ContentTypeEnum;
import com.comm.server.util.StringUtil;

/**
 * 设备服务
 * @author andy_hulibo@163.com
 * @2017年11月30日 下午4:01:40
 */
@Service("EquipmentService")
public class EquipmentService {

	private ExecutorService executorService=Executors.newCachedThreadPool();
	private Logger logger=Logger.getLogger(EquipmentService.class);
	
	@Autowired
	private Platform plateform;
	
	/**
	 * 命令处理
	 * @author andy_hulibo@163.com
	 * @2017年11月30日 下午4:17:56
	 * @param data
	 */
	public void doCmd(final PackageData data)
	{
		logger.info(String.format("invoke request serialno:%s cmd:%s content:%s",data.getSerialNo(),data.getCmd(),data.getContent()));
		final String url=plateform.getUrl();
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				Map<String,Object> params=new HashMap<String, Object>();
				params.put("key",plateform.getKey());
				params.put("message_id",data.getMessageId());
				params.put("cmd",data.getCmd());
				params.put("serialno",data.getSerialNo());
				params.put("content",data.getContent());
				String data=null;
				try {
					data=NetUtil.getHttpResponseData(url, params, RequestMethod.POST.name(),false,ContentTypeEnum.HTML);
				} catch (Exception e) {
					logger.error("invoke remote url error",e);
				}
				if(data!=null)
				{
					JSONObject json=JsonUtil.parse(data,JSONObject.class);
					boolean success=json.getBoolean("success");
					if(!success)
					{
						logger.error("invoke remote url response wrong "+json);
					}
				}
			}
		});
	}
	
	/**
	 * 设备开门
	 * @author andy_hulibo@163.com
	 * @throws Exception 
	 * @2017年11月30日 下午4:02:50
	 */
	public void open(String serialNo) throws Exception
	{
		PackageData data=new PackageData();
		data.setCmd(Cmd.CMD_OPEN);
		data.setContent(System.currentTimeMillis()+"");
		data.setSerialNo(serialNo);
		data.setMessageId(StringUtil.getRandomString(12));
		TcpUtil.write(serialNo, data);
	}
	
	/**
	 * 查询门状态
	 * @author andy_hulibo@163.com
	 * @2017年11月30日 下午4:48:12
	 * @param serialNo
	 * @throws Exception
	 */
	public void queryOpenInfo(String serialNo) throws Exception
	{
		PackageData data=new PackageData();
		data.setCmd(Cmd.CMD_QUERY_OPEN);
		data.setContent(System.currentTimeMillis()+"");
		data.setSerialNo(serialNo);
		data.setMessageId(StringUtil.getRandomString(12));
		TcpUtil.write(serialNo, data);
	}
}
