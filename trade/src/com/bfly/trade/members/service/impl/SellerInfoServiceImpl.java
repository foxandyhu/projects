package com.bfly.trade.members.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.trade.annotation.ActionModel;
import com.bfly.trade.base.service.impl.BaseServiceImpl;
import com.bfly.trade.members.entity.SellerInfo;
import com.bfly.trade.members.mapper.SellerInfoMapper;
import com.bfly.trade.members.service.ISellerInfoService;

@Transactional(propagation=Propagation.SUPPORTS)
@Service("SellerInfoServiceImpl")
@ActionModel("商户信息管理")
public class SellerInfoServiceImpl extends BaseServiceImpl<SellerInfo> implements ISellerInfoService {

	@Autowired
	private SellerInfoMapper sellerInfoMapper;
	
	@Override
	@Transactional
	public boolean updateSellerStatus(int sellerId, boolean enable) {
		return sellerInfoMapper.editSellerStatus(sellerId, enable)>0;
	}
}
