package com.lw.iot.pbj.member.service;

import com.lw.iot.pbj.core.base.service.IBaseService;
import com.lw.iot.pbj.member.entity.Member;

/**
 * 会员业务接口
 * @author 胡礼波  andy_hulibo@163.com
 * @2017年10月30日 下午4:02:59
 */
public interface IMemberService extends IBaseService<Member>{
	
	
	/**
	 * 根据微信号获得微信用户信息
	 * @author 胡礼波-Andy
	 * @2016年9月5日下午5:11:08
	 * @param weixinNo
	 * @return
	 */
	public Member getMember(String weixinNo);
	
	/**
	 * 更新微信关注状态
	 * @author 胡礼波-Andy
	 * @2016年9月5日下午5:11:25
	 * @param weixinNo
	 * @param subscribe
	 * @return
	 */
	public boolean updateSubscribeByWeixinNo(String weixinNo,boolean subscribe);
	
	/**
	 * 修改帐号状态
	 * @author 胡礼波-Andy
	 * @2015年5月10日下午6:25:47
	 * @param memberId
	 * @param enable
	 * @return
	 */
	public boolean updateMemberStatus(int memberId,boolean enable);
	
	/**
	 * 修改会员手机号码
	 * @author 胡礼波-Andy
	 * @2015年10月8日上午11:14:51
	 * @param weixinNo
	 * @param phone
	 * @return
	 */
	public boolean updateMemberPhone(String weixinNo,String phone);
}
