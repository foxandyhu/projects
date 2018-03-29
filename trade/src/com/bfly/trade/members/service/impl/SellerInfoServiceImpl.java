package com.bfly.trade.members.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.trade.annotation.ActionModel;
import com.bfly.trade.base.service.impl.BaseServiceImpl;
import com.bfly.trade.members.entity.SellerInfo;
import com.bfly.trade.members.entity.SellerPics;
import com.bfly.trade.members.mapper.SellerInfoMapper;
import com.bfly.trade.members.mapper.SellerPicsMapper;
import com.bfly.trade.members.service.ISellerInfoService;

@Transactional(propagation=Propagation.SUPPORTS)
@Service("SellerInfoServiceImpl")
@ActionModel("商户信息管理")
public class SellerInfoServiceImpl extends BaseServiceImpl<SellerInfo> implements ISellerInfoService {

	@Autowired
	private SellerInfoMapper sellerInfoMapper;
	@Autowired
	private SellerPicsMapper sellerPicsMapper;
	
	@Override
	@Transactional
	public boolean updateSellerStatus(int sellerId, boolean enable) {
		return sellerInfoMapper.editSellerStatus(sellerId, enable)>0;
	}

	@Override
	public List<SellerPics> getSellerPics(int sellerId) {
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("sellerId",sellerId);
		return sellerPicsMapper.getList(params);
	}

	@Override
	@Transactional
	@ActionModel("添加商户图片")
	public void savePics(SellerPics pic) {
		sellerPicsMapper.save(pic);
	}

	@Override
	@Transactional
	@ActionModel("删除商户图片")
	public void delPics(int sellerId, int picId) {
		SellerPics pic=sellerPicsMapper.getById(picId);
		if(pic!=null && pic.getSellerId()==sellerId)
		{
			sellerPicsMapper.delById(picId);	
		}
	}
}
