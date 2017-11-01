package com.lw.iot.pbj.api;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lw.iot.pbj.common.json.JsonUtil;
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
	 * 上传计步器数据
	 * @author andy_hulibo@163.com
	 * @2017年10月31日 下午1:36:07
	 * @param response
	 */
	@RequestMapping(value="/upd")
	public void uploadPedometerData(HttpServletResponse response)
	{
		String data=getContent();
		List<PedometerData> list=JsonUtil.parseStrToList(data,PedometerData.class);
		pedometerDataService.save(list);
		ResponseUtil.writeJson(response, data);
	}
}
