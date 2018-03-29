package com.bfly.trade.members.service;

import java.util.List;

import com.bfly.trade.base.service.IBaseService;
import com.bfly.trade.members.entity.SellerInfo;
import com.bfly.trade.members.entity.SellerPics;

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
	
	/**
	 * 获得商户图片列表
	 * @author andy_hulibo@163.com
	 * @2018年3月27日 上午11:58:14
	 * @param sellerId
	 * @return
	 */
	public List<SellerPics> getSellerPics(int sellerId);
	
	/**
	 * 添加商户图片
	 * @author andy_hulibo@163.com
	 * @2018年3月27日 上午11:59:04
	 * @param pic
	 */
	public void savePics(SellerPics pic);
	
	/**
	 * 删除商户图片
	 * @author andy_hulibo@163.com
	 * @2018年3月27日 上午11:59:25
	 * @param picId
	 */
	public void delPics(int sellerId,int picId);
}
