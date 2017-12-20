package com.lw.iot.pbj.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lw.iot.pbj.api.entity.RequestData;
import com.lw.iot.pbj.api.entity.ResponseData;
import com.lw.iot.pbj.common.json.JsonUtil;
import com.lw.iot.pbj.common.util.DataConvertUtils;
import com.lw.iot.pbj.common.util.DateUtil;
import com.lw.iot.pbj.common.util.ResponseUtil;
import com.lw.iot.pbj.equipment.entity.PedometerData;
import com.lw.iot.pbj.equipment.service.IPedometerDataService;

/**
 * 计步器读取器API接口
 * @author andy_hulibo@163.com
 * @2017年10月31日 下午2:29:47
 */
@RequestMapping(value="/api/equipment")
@Controller("PedometerReaderApiAction")
public class PedometerReaderApiAction extends BaseApiAction{

	@Autowired
	private IPedometerDataService pedometerDataService;
	
	/**
	 * 计步器与服务器通信心跳
	 * @author andy_hulibo@163.com
	 * @2017年11月2日 下午5:26:42
	 * @param response
	 */
	@RequestMapping(value="/")
	public void heartbeat(HttpServletResponse response)
	{
		ResponseData resData=ResponseData.getSuccess(null,DateUtil.formatterDateTime(new Date()));
		ResponseUtil.writeJson(response,JsonUtil.toJsonStringFilterPropter(resData).toJSONString());		
	}
	
	/**
	 * 上传计步器数据
	 * @author andy_hulibo@163.com
	 * @2017年10月31日 下午1:36:07
	 * @param response
	 */
	@RequestMapping(value="/upd")
	public void uploadPedometerData(HttpServletResponse response)
	{
		RequestData requestData=(RequestData)getRequest().getAttribute("requestData");
		String data=String.valueOf(requestData.getContent());
		String[] datas=data.split(",");
		String content=null;
		String[] items=null;
		List<PedometerData> list=new ArrayList<PedometerData>();
		PedometerData p=null;
		for(int i=0;i<datas.length;i++)
		{
			content=datas[i];
			items=content.split(":");
			p=new PedometerData();
			p.setSerialNo(items[0]);
			p.setElectricity(DataConvertUtils.convertToInteger(items[1]));
			p.setActTime(DataConvertUtils.convertToInteger(items[2]));
			p.setStep(DataConvertUtils.convertToInteger(items[3]));
			p.setVersion(Float.parseFloat(items[4]));
			p.setTime(DateUtil.parseStrDate(items[5]));
			
			list.add(p);
		}
		
		pedometerDataService.save(list);
		ResponseData resData=ResponseData.getSuccess(null,null);
		ResponseUtil.writeJson(response,JsonUtil.toJsonStringFilterPropter(resData).toJSONString());
	}
}
