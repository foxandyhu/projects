package com.lw.iot.pbj.api;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.lw.iot.pbj.chicken.entity.Chicken;
import com.lw.iot.pbj.chicken.service.IChickenService;
import com.lw.iot.pbj.common.json.JsonUtil;
import com.lw.iot.pbj.common.util.DateUtil;
import com.lw.iot.pbj.common.util.MapUtil;
import com.lw.iot.pbj.common.util.ResponseUtil;
import com.lw.iot.pbj.equipment.entity.Pedometer;
import com.lw.iot.pbj.equipment.entity.PedometerData;
import com.lw.iot.pbj.equipment.service.IPedometerDataService;
import com.lw.iot.pbj.equipment.service.IPedometerService;

/**
 * 扫描二维码获取数据Action
 * @author andy_hulibo@163.com
 * @2017年11月6日 上午9:48:19
 */
@Controller("QrcodeAction")
public class QrcodeAction extends BaseApiAction {

	@Autowired
	private IPedometerDataService pedometerDataService;
	@Autowired
	private IChickenService chickenService;
	@Autowired
	private IPedometerService pedometerService;
	
	/**
	 * 扫描二维码获取数据
	 * @author andy_hulibo@163.com
	 * @2017年11月6日 上午9:49:50
	 * @param serialNo
	 */
	@RequestMapping(value="/q/{outSerialNo}")
	public String qrcode(@PathVariable("outSerialNo") String outSerialNo,HttpServletResponse response,HttpServletRequest request)
	{
		Pedometer pedometer = pedometerService.getPedometerByQr(outSerialNo);
		String serialNo = pedometer==null?"0":pedometer.getSerialNo();
		Map<String,Object> param=new HashMap<String, Object>(2);
		param.put("serialNo",serialNo);
		List<PedometerData> list=pedometerDataService.getList(param);
		
		Map<String,Integer> monMap = new LinkedHashMap<String,Integer>(40);
		Map<String,Integer> dayMap = new LinkedHashMap<String,Integer>(80);
		String lat = "";String lon = "";
		int totalStep=0;
		if(list!=null)
		{
			Collections.reverse(list);
			String month=null;
			for (int i=0;i<list.size();i++) {
				PedometerData pedometerData = list.get(i);
				totalStep+=pedometerData.getStep();
				lat = StringUtils.isBlank(pedometerData.getLatitude())?lat:pedometerData.getLatitude();
				lon = StringUtils.isBlank(pedometerData.getLongitude())?lon:pedometerData.getLongitude();
				dayMap.put(DateUtil.formatterDate(pedometerData.getTime()), pedometerData.getStep());
				month = DateUtil.formatterDateMon(pedometerData.getTime());
				monMap.put(month, (monMap.get(month)==null?0:monMap.get(month))+pedometerData.getStep());
			}
		}
		
		JSONArray dayarray=new JSONArray();JSONArray jon=null;
		for (Map.Entry<String, Integer> entry : dayMap.entrySet()) {  
			jon = new JSONArray();
			jon.add(entry.getKey());
			jon.add(entry.getValue());
			dayarray.add(jon);
        }  
		
		JSONArray monarray=new JSONArray();JSONArray son=null;
		for (Map.Entry<String, Integer> entry : monMap.entrySet()) {  
			son = new JSONArray();
			son.add(entry.getKey());
			son.add(entry.getValue());
			monarray.add(son);
		}  
		request.setAttribute("location",MapUtil.getLocationDesc(lat,lon));
		request.setAttribute("lat",lat);
		request.setAttribute("lon",lon);
		request.setAttribute("dayDatas",dayarray.toJSONString());
		request.setAttribute("monDatas",monarray.toJSONString());
		request.setAttribute("totalStep",totalStep);
		
		Chicken chicken = chickenService.getChicken(serialNo);
		request.setAttribute("chicken", chicken);
		return "qrcode";
	}
	
	/**
	 * 实时获取当前步数
	 * @author sunships
	 * @date 2017年12月25日上午10:57:48
	 * @param serialNo
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/qrcode/{outSerialNo}/dynamic")
	public void qrcodeDynamic(@PathVariable("outSerialNo") String outSerialNo,HttpServletResponse response)
	{
		Pedometer pedometer = pedometerService.getPedometerByQr(outSerialNo);
		Map<String,Object> param=new HashMap<String, Object>(2);
		param.put("serialNo",pedometer==null?0:pedometer.getSerialNo());
		List<PedometerData> list=pedometerDataService.getList(param);
		int totalStep=0;
		if(list!=null)
		{
			for (PedometerData pedometerData : list) {
				totalStep+=pedometerData.getStep();
			}
		}
		ResponseUtil.writeJson(response, ""+totalStep);
	}
	
	/**
	 * 记步记录概览
	 * @author sunships
	 * @date 2017年12月25日上午10:57:48
	 * @return
	 */
	@RequestMapping(value="/qrcodes")
	public String qrcodes()
	{
		List<Map<Object,Object>> datas = pedometerDataService.getPedometerDataSum();
		JSONArray array=new JSONArray();
		for(Map<Object,Object> data:datas){
			array.add(data.get("outSerialNo"));
		}
		getRequest().setAttribute("list",datas);
		getRequest().setAttribute("outSerialNos",array);
		return "qrcodes";
	}
	
	/**
	 * 动态更新最新总步数
	 * @author sunships
	 * @date 2017年12月25日下午5:09:01
	 */
	@RequestMapping(value="/qrcodes/dynamic")
	public void qrcodesDynamic(HttpServletResponse response){
		List<Map<Object,Object>> datas = pedometerDataService.getPedometerDataSum();
		ResponseUtil.writeJson(response, JsonUtil.toJsonStringFilterPropterForArray(datas, "").toJSONString());
	}
	
	public String getActTimeStr(int seconds)
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
