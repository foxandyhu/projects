package com.bfly.trade.message.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bfly.trade.base.action.BaseAction;
import com.bfly.trade.message.entity.SmsHistory;
import com.bfly.trade.message.service.ISmsGetaway;
import com.bfly.trade.message.service.ISmsHistoryService;
import com.bfly.trade.page.Pager;
import com.bfly.trade.util.DataConvertUtils;
import com.bfly.trade.util.JsonUtil;
import com.bfly.trade.util.ResponseUtil;

/**
 * 短信记录Action
 * @author 胡礼波-Andy
 * @2017年1月9日上午11:14:15
 */
@Controller("SmsHistoryAction")
@RequestMapping(value="/manage/sms")
public class SmsHistoryAction extends BaseAction {

	@Autowired
	private ISmsHistoryService smsService;
	@Autowired
	private ISmsGetaway smsGetaway;
	
	/**
	 * 短信记录
	 * @author 胡礼波-Andy
	 * @2017年1月9日上午11:15:17
	 * @return
	 */
	@RequestMapping(value="")
	public void list(HttpServletResponse response)
	{
		instantPage(10);
		
		Map<String,Object> params=new HashMap<String, Object>();
		
		if(getRequest().getParameter("status")!=null)
		{
			int status=DataConvertUtils.convertToInteger(getRequest().getParameter("status"));
			if(status!=-1)
			{
				params.put("status",status);
			}
		}
		
		List<SmsHistory> list=smsService.getList(params);
		int total=smsService.getCount(params);
		Pager pager=new Pager(getPage(), getRows(), total);
		pager.setDatas(list);
		ResponseUtil.writeJson(response, JsonUtil.toJsonStringFilterPropter(pager).toJSONString());
	}
	
	/**
	 * 重新发送短信
	 * @author 胡礼波-Andy
	 * @2017年1月9日上午11:37:14
	 */
	@RequestMapping(value="/resend/{smsId}")
	public void resendSms(@PathVariable("smsId") int smsId,HttpServletResponse response)
	{
		SmsHistory history=smsService.get(smsId);
		smsGetaway.send(history);
		history.setTotal(history.getTotal()+1);
		smsService.edit(history);
		ResponseUtil.writeJavascript(response, "");
	}
}
