package com.comm.server.api;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.comm.server.entity.Cmd;
import com.comm.server.entity.ResponseData;
import com.comm.server.equipment.EquipmentService;
import com.comm.server.util.JsonUtil;
import com.comm.server.util.ResponseUtil;

@Controller("EquipmentAction")
@RequestMapping(value="/api/equipment")
public class EquipmentAction {
	
	@Autowired
	private EquipmentService equipmentService;
	
	/**
	 * 开门
	 * @author andy_hulibo@163.com
	 * @throws Exception 
	 * @2017年11月30日 下午3:51:15
	 */
	@RequestMapping(value="/open/{serialno}")
	public void open(@PathVariable("serialno") String serialno,HttpServletResponse response) throws Exception
	{
		String time=System.currentTimeMillis()+"";
		equipmentService.open(serialno);
		ResponseData data=ResponseData.getSuccessData(Cmd.CMD_OPEN,time,serialno);
		ResponseUtil.writeJson(response, JsonUtil.toJsonStringFilterPropter(data).toJSONString());
	}
	
	/**
	 * 查询门状态
	 * @author andy_hulibo@163.com
	 * @2017年11月30日 下午4:47:10
	 */
	@RequestMapping(value="/query/{serialno}")
	public void queryOpenInfo(@PathVariable("serialno") String serialno,HttpServletResponse response) throws Exception
	{
		String time=System.currentTimeMillis()+"";
		equipmentService.queryOpenInfo(serialno);
		ResponseData data=ResponseData.getSuccessData(Cmd.CMD_QUERY_OPEN,time,serialno);
		ResponseUtil.writeJson(response, JsonUtil.toJsonStringFilterPropter(data).toJSONString());
	}
}
