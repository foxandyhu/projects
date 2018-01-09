package com.lw.iot.pbj.equipment.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lw.iot.pbj.common.json.JsonUtil;
import com.lw.iot.pbj.common.page.Pager;
import com.lw.iot.pbj.common.util.ResponseUtil;
import com.lw.iot.pbj.core.base.action.BaseAction;
import com.lw.iot.pbj.equipment.entity.PedometerData;
import com.lw.iot.pbj.equipment.service.IPedometerDataService;

/**
 * 脚环计步器数据Action
 * @author andy_hulibo@163.com
 * @2017年10月31日 下午6:22:27
 */
@Controller("PedometerDataAction")
@RequestMapping(value = "/manage/pedometer/data")
public class PedometerDataAction extends BaseAction {
	
	@Autowired
	private IPedometerDataService pedometerDataService;

	/**
	 * 脚环计步器数据集合
	 * @author andy_hulibo@163.com
	 * @2017年10月31日 下午3:40:01
	 * @param response
	 */
	@RequestMapping(value = "/list")
	public void list(HttpServletResponse response) {
		instantPage(10);
		List<PedometerData> list = pedometerDataService.getList();
		int total = pedometerDataService.getCount();
		
		Pager pager=new Pager(super.getPage(),super.getRows(),total);
		
		pager.setDatas(list);
		ResponseUtil.writeJson(response, JsonUtil.toJsonStringFilterPropter(pager).toJSONString());
	}
}
