package com.lw.iot.pbj.equipment.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lw.iot.pbj.common.json.JsonUtil;
import com.lw.iot.pbj.common.page.Pager;
import com.lw.iot.pbj.common.util.ResponseUtil;
import com.lw.iot.pbj.core.base.action.BaseAction;
import com.lw.iot.pbj.equipment.entity.Pedometer;
import com.lw.iot.pbj.equipment.service.IPedometerService;

/**
 * 脚环计步器Action
 * @author andy_hulibo@163.com
 * @2017年10月31日 下午6:22:36
 */
@Controller("PedometerAction")
@RequestMapping(value = "/manage/pedometer")
public class PedometerAction extends BaseAction {
	
	@Autowired
	private IPedometerService pedometerService;

	/**
	 * 脚环计步器集合
	 * @author andy_hulibo@163.com
	 * @2017年10月31日 下午3:40:01
	 * @param response
	 */
	@RequestMapping(value = "/list")
	public void list(HttpServletResponse response) {
		instantPage(10);
		List<Pedometer> list = pedometerService.getList();
		int total = pedometerService.getCount();
		
		Pager pager=new Pager(super.getPage(),super.getRows(),total);
		
		pager.setDatas(list);
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
	public void addPedometerReader(HttpServletResponse response,Pedometer pedometer) {
		pedometerService.save(pedometer);
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
	public void viewPedometer(HttpServletResponse response,@PathVariable("serialNo") String serialNo){
		Pedometer pedometer = pedometerService.getPedometer(serialNo);
		ResponseUtil.writeJson(response, JsonUtil.toJsonStringFilterPropter(pedometer).toJSONString());
	}

	/**
	 * 删除脚环计步器
	 * @author andy_hulibo@163.com
	 * @2017年10月31日 下午3:50:08
	 * @param response 输出流
	 * @param serialNo 设备编号
	 */
	@RequestMapping(value = "/del/{serialNo}")
	public void delPedometerReader(HttpServletResponse response,@PathVariable("serialNo") String serialNo) {
		pedometerService.del(serialNo);
		ResponseUtil.writeJson(response, "");
	}
}
