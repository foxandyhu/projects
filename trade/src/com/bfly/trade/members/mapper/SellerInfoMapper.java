package com.bfly.trade.members.mapper;

import org.apache.ibatis.annotations.Param;

import com.bfly.trade.base.mapper.BaseMapper;
import com.bfly.trade.members.entity.SellerInfo;

public interface SellerInfoMapper extends BaseMapper<SellerInfo> {

	/**
	 * 修改商户状态
	 * @author andy_hulibo@163.com
	 * @2018年1月12日 上午10:24:28
	 * @param sellerId
	 * @param enable
	 * @return
	 */
	public int editSellerStatus(@Param("sellerId")int sellerId,@Param("enable")boolean enable);
}
