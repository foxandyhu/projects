package com.bfly.industry.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bfly.industry.members.service.SellerInfoService;

/**
 * 首页
 * @author andy_hulibo@163.com
 * @2018年3月30日 下午1:49:20
 */
@Controller("IndexAction")
public class IndexAction extends BaseAction {

	@Autowired
	@Qualifier("sellerInfoService")
	private SellerInfoService sellerInfoService;
	
	/**
	 * 首页
	 * @author andy_hulibo@163.com
	 * @2018年3月30日 下午3:05:33
	 */
	@RequestMapping(value="")
	public void index()
	{
	}
}
