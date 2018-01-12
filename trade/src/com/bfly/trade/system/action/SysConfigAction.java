package com.bfly.trade.system.action;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bfly.trade.base.action.BaseAction;
import com.bfly.trade.system.entity.SysConfig;
import com.bfly.trade.system.service.ISysConfigService;
import com.bfly.trade.util.JsonUtil;
import com.bfly.trade.util.ResponseUtil;

/**
 * 系统配置action
 * @author 胡礼波-Andy
 * @2015年5月22日下午5:55:11
 */
@Controller("SysConfigAction")
@RequestMapping(value="/manage/system")
public class SysConfigAction extends BaseAction{

	@Autowired
	private ISysConfigService sysConfigService;
	
	/**
	 * 加载系统配置
	 * @author 胡礼波-Andy
	 * @2015年5月22日下午5:55:56
	 * @return
	 */
	@RequestMapping(value="/config/load")
	public void loadConfig(HttpServletResponse response)
	{
		SysConfig config=sysConfigService.getSysConfig();
		ResponseUtil.writeJson(response, JsonUtil.toJsonStringFilterPropter(config).toJSONString());
	}
	
	/**
	 * 修改系统配置
	 * @author 胡礼波-Andy
	 * @2015年5月22日下午5:57:37
	 * @param config
	 * @return
	 */
	@RequestMapping(value="/config/post")
	public void addConfig(SysConfig config,HttpServletResponse response)
	{
		sysConfigService.save(config);
		getRequest().getServletContext().setAttribute("sysConfig",config);
		ResponseUtil.writeJson(response, JsonUtil.toJsonStringFilterPropter(config).toJSONString());
	}
	
}
