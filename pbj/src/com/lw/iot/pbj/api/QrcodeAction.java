package com.lw.iot.pbj.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lw.iot.pbj.common.util.DateUtil;
import com.lw.iot.pbj.equipment.entity.PedometerData;
import com.lw.iot.pbj.equipment.service.IPedometerDataService;

/**
 * 扫描二维码获取数据Action
 * @author andy_hulibo@163.com
 * @2017年11月6日 上午9:48:19
 */
@Controller("QrcodeAction")
public class QrcodeAction extends BaseApiAction {

	@Autowired
	private IPedometerDataService pedometerDataService;
	
	/**
	 * 扫描二维码获取数据
	 * @author andy_hulibo@163.com
	 * @2017年11月6日 上午9:49:50
	 * @param serialNo
	 */
	@RequestMapping(value="/qrcode/{serialNo}")
	public String qrcode(@PathVariable("serialNo") String serialNo,HttpServletResponse response)
	{
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("serialNo",serialNo);
		List<PedometerData> list=pedometerDataService.getList(param);
		JSONArray array=new JSONArray();
		int totalStep=0;
		if(list!=null)
		{
			JSONObject json=null;
			for (PedometerData pedometerData : list) {
				json=new JSONObject();
				totalStep+=pedometerData.getStep();
				json.put("step", pedometerData.getStep());
				json.put("time",DateUtil.formatterDate(pedometerData.getTime()));
				
				String actTime=getActTimeStr(pedometerData.getActTime());
				json.put("actTime",actTime);
				array.add(json);
			}
		}
		getRequest().setAttribute("list",array);
		getRequest().setAttribute("totalStep",totalStep);
		return "qrcode";
	}
	
	private String getActTimeStr(int seconds)
	{
		Double h=seconds/(60*60d);
		Double f=new Double(h);
		//小时
		int hour=f.intValue();
		
		f=new Double((f-hour)*60);
		//分钟
		int minute=f.intValue();
		//秒
		int second=new Double((f-minute)*60).intValue();
		
		String actTime="";
		if(hour>0)
		{
			actTime=hour+"时  ";
		}
		if(minute>0)
		{
			actTime=actTime+minute+"分  ";
		}
		if(second>0)
		{
			actTime=actTime+second+"秒  ";
		}
		return actTime;
	}
}
