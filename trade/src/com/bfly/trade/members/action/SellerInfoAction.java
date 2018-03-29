package com.bfly.trade.members.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bfly.trade.base.action.BaseAction;
import com.bfly.trade.members.entity.SellerInfo;
import com.bfly.trade.members.entity.SellerPics;
import com.bfly.trade.members.service.ISellerInfoService;
import com.bfly.trade.page.Pager;
import com.bfly.trade.util.JsonUtil;
import com.bfly.trade.util.ResponseUtil;

/**
 * 会员信息Action
 * @author 胡礼波-Andy
 * @2016年9月5日下午4:58:50
 */
@Controller("SellerInfoAction")
@RequestMapping(value="/manage/seller")
public class SellerInfoAction extends BaseAction{
	@Autowired
	private ISellerInfoService sellerInfoService;

	/**
	 * 商户列表
	 * @author andy_hulibo@163.com
	 * @2018年1月12日 上午10:17:05
	 * @param response
	 */
	@RequestMapping(value="")
	public void list(HttpServletResponse response){
		instantPage(10);
		List<SellerInfo> list=sellerInfoService.getList();
		int total=sellerInfoService.getCount();
		Pager pager=new Pager(super.getPage(),super.getRows(),total);
		pager.setDatas(list);
		ResponseUtil.writeJson(response,JsonUtil.toJsonStringFilterPropter(pager).toJSONString());
	}

	/**
	 * 商户详情
	 * @author andy_hulibo@163.com
	 * @2018年1月12日 上午10:18:00
	 * @param response
	 * @param id
	 */
	@RequestMapping(value="/{sellerId}")
	public void sellerInfoDetail(HttpServletResponse response,@PathVariable("sellerId")int sellerId){
		SellerInfo sellerInfo = sellerInfoService.get(sellerId);
		ResponseUtil.writeJson(response,JsonUtil.toJsonStringFilterPropter(sellerInfo).toJSONString());
	}

	/**
	 * 修改商户状态
	 * @author andy_hulibo@163.com
	 * @2018年1月12日 上午10:18:50
	 * @param memberId
	 * @param response
	 */
	@RequestMapping(value="/editstatus/{sellerId}")
	public void editMemberStatus(@PathVariable("sellerId") int sellerId,HttpServletResponse response)
	{
		SellerInfo sellerInfo = sellerInfoService.get(sellerId);
		sellerInfoService.updateSellerStatus(sellerId,!sellerInfo.isEnable());
		ResponseUtil.writeJson(response, null);
	}
	
	/**
	 * 商户图片列表
	 * @author andy_hulibo@163.com
	 * @2018年3月27日 上午11:56:59
	 * @param response
	 */
	@RequestMapping(value="/{sellerId}/pics")
	public void listPics(@PathVariable("sellerId") int sellerId,HttpServletResponse response){
		List<SellerPics> list=sellerInfoService.getSellerPics(sellerId);
		ResponseUtil.writeJson(response,JsonUtil.toJsonStringFilterPropterForArray(list).toJSONString());
	}

	/**
	 * 删除商户图片
	 * @author andy_hulibo@163.com
	 * @2018年3月27日 下午12:06:39
	 * @param sellerId
	 * @param picId
	 * @param response
	 */
	@RequestMapping(value="/del/pics/{sellerId}-{picId}")
	public void delPics(@PathVariable("sellerId")int sellerId,@PathVariable("picId")int picId,HttpServletResponse response)
	{
		sellerInfoService.delPics(sellerId, picId);
		ResponseUtil.writeJson(response, "");
	}
}
