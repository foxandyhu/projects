package com.lw.iot.pbj.chicken.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lw.iot.pbj.chicken.entity.Chicken;
import com.lw.iot.pbj.chicken.service.IChickenService;
import com.lw.iot.pbj.common.json.JsonUtil;
import com.lw.iot.pbj.common.page.Pager;
import com.lw.iot.pbj.common.util.ResponseUtil;
import com.lw.iot.pbj.core.base.action.BaseAction;

/**
 * 鸡只管理类
 * @author sunships
 * @date 2018年1月18日上午10:58:06
 */
@Controller
@RequestMapping("/manage/chicken")
public class ChickenAction extends BaseAction{

	@Autowired
	private IChickenService chickenService;
	
	/**
	 * 查看鸡只集合
	 * @author sunships
	 * @date 2018年1月18日下午11:00:06
	 * @param response
	 */
	@RequestMapping("/list")
	public void list(HttpServletResponse response){
		instantPage(10);
		List<Chicken> list = chickenService.getList();
		int count = chickenService.getCount();
		Pager pager = new Pager(getRows(),getPage(),count);
		pager.setDatas(list);
		ResponseUtil.writeJson(response, JsonUtil.toJsonStringFilterPropter(pager).toJSONString());
	}
	
	/**
	 * 新增鸡只
	 * @author sunships
	 * @date 2018年1月18日下午11:02:20
	 * @param response
	 * @param c
	 */
	@RequestMapping(value = "post")
	public void save(HttpServletResponse response,Chicken c){
		chickenService.save(c);
		ResponseUtil.writeJson(response,"");
	}
	
	/**
	 * 删除鸡只
	 * @author sunships
	 * @date 2018年1月18日下午11:04:33
	 * @param response
	 * @param serialNo
	 */
	@RequestMapping(value = "/del/{serialNo}")
	public void del(HttpServletResponse response,@PathVariable("serialNo")String serialNo){
		chickenService.del(serialNo);
		ResponseUtil.writeJson(response, "");
	}
	
	/**
	 * 鸡只详情
	 * @author sunships
	 * @date 2018年1月22日下午3:37:59
	 * @param response
	 * @param c
	 */
	@RequestMapping(value = "/detail/{serialNo}")
	public void detail(HttpServletResponse response,@PathVariable("serialNo")String serialNo){
		chickenService.getChicken(serialNo);
		ResponseUtil.writeJson(response,"");
	}
	
	
	/**
	 * 编辑鸡只
	 * @author sunships
	 * @date 2018年1月22日下午3:37:59
	 * @param response
	 * @param c
	 */
	@RequestMapping(value = "/edit")
	public void update(HttpServletResponse response,Chicken c){
		chickenService.edit(c);
		ResponseUtil.writeJson(response,"");
	}
	
	/**
	 * 标记为已出栏
	 * @author sunships
	 * @date 2018年1月22日下午3:58:09
	 * @param response
	 * @param id
	 */
	@RequestMapping(value = "/status")
	public void updateStatus(HttpServletResponse response,String id){
		chickenService.updateStatus(id);
		ResponseUtil.writeJson(response,"");
	}
}
