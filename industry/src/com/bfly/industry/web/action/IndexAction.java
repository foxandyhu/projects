package com.bfly.industry.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bfly.industry.enums.SellerType;
import com.bfly.industry.members.entity.SellerInfo;
import com.bfly.industry.members.entity.SellerPics;
import com.bfly.industry.members.service.SellerInfoService;
import com.bfly.industry.util.ResponseUtil;

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
	@RequestMapping(value="/index")
	public String index()
	{
		
		HttpServletRequest request=getRequest();
		
		List<SellerInfo> recommends=sellerInfoService.getRecommendSellers();
		
		request.setAttribute("recommends", recommends);
		request.setAttribute("sellerTypes",SellerType.values());
		return "index_";
	}
	
	/**
	 * 不同类型的商户
	 * @author andy_hulibo@163.com
	 * @2018年4月8日 下午3:10:14
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/type/{type}")
	public String getSellerByType(@PathVariable("type") int type)
	{
		HttpServletRequest request=getRequest();
		SellerType sellerType=SellerType.getSellerType(type);
		request.setAttribute("typeName",sellerType!=null?sellerType.getName():null);
		request.setAttribute("typeId",sellerType!=null?sellerType.getId():null);
		return "industry_list";
	}
	
	/**
	 * Ajax分页显示
	 * @author andy_hulibo@163.com
	 * @2018年4月9日 上午10:02:14
	 * @param type
	 * @param pageNo
	 */
	@RequestMapping(value="/load/{type}-{pageNo}")
	public void loadSellers(@PathVariable("type")int type,@PathVariable("pageNo")int pageNo,HttpServletResponse response)
	{
		List<SellerInfo> list=sellerInfoService.getSellersByType(type,pageNo);
		JSONArray array=new JSONArray();
		JSONObject json=null;
		for (SellerInfo sellerInfo:list) {
			json=new JSONObject();
			json.put("id",sellerInfo.getId());
			json.put("logo",sellerInfo.getLogo());
			json.put("name",sellerInfo.getName());
			json.put("clickRate", sellerInfo.getClickRate());
			array.add(json);
		}
		ResponseUtil.writeJson(response, array.toJSONString());
	}
	
	/**
	 * 商户详情
	 * @author andy_hulibo@163.com
	 * @2018年4月8日 下午3:12:05
	 * @param sellerId
	 * @return
	 */
	@RequestMapping(value="/{sellerId}")
	public String detailSellerInfo(@PathVariable("sellerId")String sellerId)
	{
		HttpServletRequest request=getRequest();
		List<SellerPics> pics=sellerInfoService.getSellerPics(sellerId);
		SellerInfo sellerInfo=sellerInfoService.getSellerById(sellerId);
		
		request.setAttribute("pics",pics);
		request.setAttribute("sellerInfo",sellerInfo);
		
		return "industry_detail";
	}
}
