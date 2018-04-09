package com.bfly.industry.members.service;

import java.util.List;

import com.bfly.industry.members.entity.SellerInfo;

import feign.Param;
import feign.RequestLine;

public interface SellerInfoService{

	/**
	 * 获得推荐商户
	 * @author andy_hulibo@163.com
	 * @2018年4月8日 下午2:48:29
	 * @return
	 */
	@RequestLine("GET /api/seller/recommend")
	public List<SellerInfo> getRecommendSellers();
	
	/**
	 * 获得所有的商户
	 * @author andy_hulibo@163.com
	 * @2018年4月8日 下午3:02:32
	 * @return
	 */
	@RequestLine("GET /api/seller/list?pageNo={pageNo}")
	public List<SellerInfo> getAllSellers(@Param(value="pageNo") int pageNo);
	
	/**
	 * 获得不通类型的商户
	 * @author andy_hulibo@163.com
	 * @2018年4月8日 下午3:07:09
	 * @param pageNo
	 * @return
	 */
	@RequestLine("GET /api/seller/type/{type}-{pageNo}")
	public List<SellerInfo> getSellersByType(@Param(value="type") int type,@Param(value="pageNo") int pageNo);
	
	/**
	 * 商户详情
	 * @author andy_hulibo@163.com
	 * @2018年4月8日 下午3:08:42
	 * @param sellerId
	 * @return
	 */
	@RequestLine("GET /api/seller/{sellerId}")
	public SellerInfo getSellerById(@Param(value="sellerId") String sellerId);
}
