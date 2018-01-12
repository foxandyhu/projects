package com.bfly.trade.members.service;

import com.bfly.trade.base.service.IBaseService;
import com.bfly.trade.members.entity.SellerInfo;

public interface ISellerInfoService extends IBaseService<SellerInfo> {

	/**
	 * 修改商户是否启用状态
	 * @author andy_hulibo@163.com
	 * @2018年1月12日 上午10:19:58
	 * @param sellerId
	 * @param enable
	 * @return
	 */
	public boolean updateSellerStatus(int sellerId,boolean enable);
}
