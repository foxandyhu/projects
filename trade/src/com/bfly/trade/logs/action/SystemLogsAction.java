package com.bfly.trade.logs.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bfly.trade.base.action.BaseAction;
import com.bfly.trade.logs.entity.LoginLogs;
import com.bfly.trade.logs.entity.SysLog;
import com.bfly.trade.logs.service.ILogService;
import com.bfly.trade.logs.service.ILoginLogsService;
import com.bfly.trade.page.Pager;
import com.bfly.trade.util.JsonUtil;
import com.bfly.trade.util.ResponseUtil;

/**
 * 系统日志Action
 * @author 胡礼波-Andy
 * @2014年11月11日下午5:40:06
 */
@Controller("SystemLogsAction")
@RequestMapping(value="/manage")
public class SystemLogsAction extends BaseAction {

	@Autowired
	private ILogService logService;
	@Autowired
	private ILoginLogsService loginLogService;
	
	/**
	 * 获得日志列表
	 * @author 胡礼波-Andy
	 * @2014年11月11日下午5:44:50
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/logs")
	public void logList(HttpServletResponse response)
	{
		instantPage(20);
		List<SysLog> list=logService.getList(null);
		int total=logService.getCount();
		Pager pager=new Pager(getPage(), getRows(), total);
		pager.setDatas(list);
		getRequest().setAttribute("pager",pager);
		ResponseUtil.writeJson(response, JsonUtil.toJsonStringFilterPropter(pager).toJSONString());
	}
	
	/**
	 * 登录日志
	 * @author 胡礼波-Andy
	 * @2016年9月23日下午5:08:09
	 */
	@RequestMapping(value="/login/logs")
	public void loginLogList(HttpServletResponse response)
	{
		instantPage(20);
		List<LoginLogs> list=loginLogService.getList(null);
		int total=loginLogService.getCount();
		Pager pager=new Pager(getPage(), getRows(), total);
		pager.setDatas(list);
		getRequest().setAttribute("pager",pager);
		ResponseUtil.writeJson(response, JsonUtil.toJsonStringFilterPropter(pager).toJSONString());
	}
}
