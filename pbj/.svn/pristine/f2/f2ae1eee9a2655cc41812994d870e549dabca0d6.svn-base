package com.lw.iot.pbj.equipment.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.lw.iot.pbj.common.json.JsonUtil;
import com.lw.iot.pbj.common.page.Pager;
import com.lw.iot.pbj.common.util.ResponseUtil;
import com.lw.iot.pbj.core.base.action.BaseAction;
import com.lw.iot.pbj.equipment.entity.PedometerReader;
import com.lw.iot.pbj.equipment.service.IPedometerReaderService;

/**
 * 计步器阅读器Action
 * @author andy_hulibo@163.com
 * @2017年10月31日 下午6:22:27
 */
@Controller("PedometerReaderAction")
@RequestMapping(value = "/manage/pedometer/reader")
public class PedometerReaderAction extends BaseAction {
	
	@Autowired
	private IPedometerReaderService pedometerReaderService;

	/**
	 * 计步器阅读器集合
	 * @author andy_hulibo@163.com
	 * @2017年10月31日 下午3:40:01
	 * @param response
	 */
	@RequestMapping(value = "/list")
	public void list(HttpServletResponse response) {
		instantPage(10);
		List<PedometerReader> list = pedometerReaderService.getList();
		int total = pedometerReaderService.getCount();
		
		Pager pager=new Pager(super.getPage(),super.getRows(),total);
		
		JSONArray array=JsonUtil.toJsonStringFilterPropterForArray(list,"remark");
		
		pager.setDatas(array);
		ResponseUtil.writeJson(response, JsonUtil.toJsonStringFilterPropter(pager).toJSONString());
	}

	
	/**
	 * 新增设备
	 * @author andy_hulibo@163.com
	 * @2017年10月31日 下午4:31:01
	 * @param response
	 * @param cabinet
	 */
	@RequestMapping(value = "/post")
	public void addPedometerReader(HttpServletResponse response,PedometerReader reader) {
		pedometerReaderService.save(reader);
		ResponseUtil.writeJson(response,"");
	}
	

	/**
	 * 查看设备详情
	 * @author andy_hulibo@163.com
	 * @2017年10月31日 下午4:43:52
	 * @param response
	 * @param serialNo
	 */
	@RequestMapping(value = "/{serialNo}")
	public void viewPedometerReader(HttpServletResponse response,@PathVariable("serialNo") String serialNo){
		PedometerReader reader = pedometerReaderService.getPedometerReader(serialNo);
		ResponseUtil.writeJson(response, JsonUtil.toJsonStringFilterPropter(reader).toJSONString());
	}

	/**
	 * 修改设备
	 * @author andy_hulibo@163.com
	 * @2017年10月31日 下午4:51:21
	 * @param response
	 * @param cabinet
	 */
	@RequestMapping(value = "/edit")
	public void editPedometerReader(HttpServletResponse response, PedometerReader reader) {
		pedometerReaderService.edit(reader);
		ResponseUtil.writeJson(response,"");
	}

	/**
	 * 删除计步器阅读器
	 * @author andy_hulibo@163.com
	 * @2017年10月31日 下午3:50:08
	 * @param response 输出流
	 * @param serialNo 设备编号
	 */
	@RequestMapping(value = "/del/{serialNo}")
	public void delPedometerReader(HttpServletResponse response,@PathVariable("serialNo") String serialNo) {
		pedometerReaderService.del(serialNo);
		ResponseUtil.writeJson(response, "");
	}

	/**
	 * 启用禁用设备
	 * @author andy_hulibo@163.com
	 * @2017年10月31日 下午3:56:10
	 * @param response
	 * @param serialNo 设备编号
	 */
	@RequestMapping(value="/enable/{serialNo}")
	public void enablePedometerReader(HttpServletResponse response, @PathVariable("serialNo") String serialNo)
	{
		PedometerReader reader = pedometerReaderService.getPedometerReader(serialNo);
		pedometerReaderService.enable(serialNo, !reader.isEnable());
		ResponseUtil.writeJson(response, "");
	}
	
}
