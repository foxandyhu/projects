package com.bfly.trade.members.action;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bfly.trade.base.action.BaseAction;
import com.bfly.trade.members.entity.Members;
import com.bfly.trade.members.service.IMembersService;
import com.bfly.trade.page.Pager;
import com.bfly.trade.util.DataConvertUtils;
import com.bfly.trade.util.DateUtil;
import com.bfly.trade.util.JsonUtil;
import com.bfly.trade.util.ResponseUtil;

/**
 * 会员信息Action
 * @author 胡礼波-Andy
 * @2016年9月5日下午4:58:50
 */
@Controller("MemberAction")
@RequestMapping(value="/manage/member")
public class MemberAction extends BaseAction{
	@Autowired
	private IMembersService memberService;

	/**
	 * 会员列表
	 * @author 胡礼波-Andy
	 * @2015年4月13日
	 * @param request
	 * @return
	 */
	@RequestMapping(value="")
	public void list(HttpServletResponse response){
		instantPage(10);
		HttpServletRequest request=getRequest();
		String no=request.getParameter("no");
		Map<String,Object> param=new  HashMap<String, Object>();
		if(!StringUtils.isBlank(no))
		{
			param.put("no",no);
			request.setAttribute("no",no);
		}
		if(!StringUtils.isBlank(request.getParameter("subscribe")))
		{
			int subscribe=DataConvertUtils.convertToInteger(request.getParameter("subscribe"));
			if(subscribe!=-1)
			{
				param.put("subscribe",subscribe);
				request.setAttribute("subscribe",subscribe);
			}
		}
		if(!StringUtils.isBlank(request.getParameter("sex")))
		{
			int sex=DataConvertUtils.convertToInteger(request.getParameter("sex"));
			if(sex!=-1)
			{
				param.put("sex",sex);
				request.setAttribute("sex",sex);
			}
		}
		Date begin=DateUtil.parseStrDate(request.getParameter("beginTime"));
		Date end=DateUtil.parseStrDate(request.getParameter("endTime"));
		if(begin!=null)
		{
			param.put("beginTime", begin);
			request.setAttribute("beginTime",begin);
		}
		if(end!=null)
		{
			param.put("endTime", end);
			request.setAttribute("endTime",end);
		}
		
		List<Members> list=memberService.getList(param);
		int total=memberService.getCount(param);
		Pager pager=new Pager(super.getPage(),super.getRows(),total);
		pager.setDatas(list);
		ResponseUtil.writeJson(response,JsonUtil.toJsonStringFilterPropter(pager).toJSONString());
	}

	/**
	 * 
	 * @author 胡礼波-Andy
	 * @2015年4月28日 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}")
	public void view(HttpServletResponse response,@PathVariable("id")int id){
		Members member = memberService.get(id);
		ResponseUtil.writeJson(response,JsonUtil.toJsonStringFilterPropter(member).toJSONString());
	}

	/**
	 * 帐号状态更改
	 * @author 胡礼波-Andy
	 * @2015年5月10日下午6:19:52
	 * @param memberId
	 * @return
	 */
	@RequestMapping(value="/editstatus/{memberId}")
	public void editMemberStatus(@PathVariable("memberId") int memberId,HttpServletResponse response)
	{
		Members member = memberService.get(memberId);
		memberService.updateMemberStatus(memberId,!member.isEnable());
		ResponseUtil.writeJson(response, null);
	}
}
