package com.bfly.industry.members.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bfly.industry.members.entity.SellerPics;
import com.bfly.industry.members.service.ISellerInfoService;

@Service("SellerInfoServiceImpl")
public class SellerInfoServiceImpl implements ISellerInfoService {

	@Override
	public List<SellerPics> getSellerPics(int sellerId) {
		return null;
	}

	@Override
	public void savePics(SellerPics pic) {
	}

	@Override
	public void delPics(int sellerId, int picId) {
		
	}
}
