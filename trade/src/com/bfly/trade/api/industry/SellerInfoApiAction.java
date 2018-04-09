package com.bfly.trade.api.industry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bfly.trade.api.BaseApiAction;
import com.bfly.trade.members.entity.SellerInfo;
import com.bfly.trade.members.service.ISellerInfoService;
import com.bfly.trade.util.JsonUtil;
import com.bfly.trade.util.ResponseUtil;
import com.bfly.trade.util.WebUtil;

/**
 * 商户API Action
 * @author andy_hulibo@163.com
 * @2018年4月8日 上午11:17:39
 */
@Controller("SellerInfoApiAction")
@RequestMapping(value="/api/seller")
public class SellerInfoApiAction extends BaseApiAction{

	@Autowired
	private ISellerInfoService sellerInfoService;
	
	/**
	 * 获得推荐的商户
	 * @author andy_hulibo@163.com
	 * @2018年4月8日 上午11:18:53
	 */
	@RequestMapping(value="/recommend")
	public void getRecommendSellers(HttpServletResponse response)
	{
		instantPage(4);
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("recommend", true);
		List<SellerInfo> sellerInfos=sellerInfoService.getList(param);
		
		JSONArray array=parseJsonArray(sellerInfos);
		ResponseUtil.writeJson(response, array.toJSONString());
	}
	
	/**
	 * 获得所有商户
	 * @author andy_hulibo@163.com
	 * @2018年4月8日 上午11:25:13
	 */
	@RequestMapping(value="/list")
	public void getAllSellers(HttpServletResponse response)
	{
		instantPage(10);
		List<SellerInfo> sellerInfos = sellerInfoService.getList();
		
		JSONArray array=parseJsonArray(sellerInfos);
		ResponseUtil.writeJson(response, array.toJSONString());
	}
	
	/**
	 * 获得不通类型的商户
	 * @author andy_hulibo@163.com
	 * @2018年4月8日 下午2:40:37
	 * @param type
	 * @param response
	 */
	@RequestMapping(value="/type/{type}-{pageNo}")
	public void getSellersByType(@PathVariable("type")int type,@PathVariable("pageNo")int pageNo,HttpServletResponse response)
	{
		instantPage(pageNo,10);
		Map<String,Object> param=new HashMap<String, Object>();
		if(type>0)
		{
			param.put("type", type);
		}
		
		List<SellerInfo> sellerInfos = sellerInfoService.getList(param);
		
		JSONArray array=parseJsonArray(sellerInfos);
		ResponseUtil.writeJson(response, array.toJSONString());
	}
	
	private JSONArray parseJsonArray(List<SellerInfo> sellerInfos)
	{
		JSONArray array=new JSONArray();
		if(sellerInfos!=null)
		{
			JSONObject json=null;
			for (SellerInfo sellerInfo : sellerInfos) {
				json=new JSONObject();
				json.put("id",WebUtil.IdConvert(sellerInfo.getId()));
				json.put("logo",sellerInfo.getLogo());
				json.put("name",sellerInfo.getName());
				json.put("clickRate",sellerInfo.getClickRate());
				array.add(json);
			}
		}
		return array;
	}
	
	/**
	 * 商户详情
	 * @author andy_hulibo@163.com
	 * @2018年4月8日 上午11:38:59
	 * @param response
	 */
	@RequestMapping(value="/{sellerId}")
	public void detailSellerInfo(@PathVariable("sellerId") String sellerId, HttpServletResponse response)
	{
		int sid=WebUtil.IdConvert(sellerId);
		SellerInfo sellerInfo = sellerInfoService.get(sid);
		JSONObject json=JsonUtil.toJsonStringFilterPropter(sellerInfo);
		ResponseUtil.writeJson(response, json.toJSONString());
	}
}
