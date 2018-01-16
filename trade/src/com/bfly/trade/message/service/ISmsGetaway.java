package com.bfly.trade.message.service;

import com.bfly.trade.base.service.IBaseService;
import com.bfly.trade.message.entity.SmsHistory;

/**
 * 短信网关
 * @author andy_hulibo@163.com
 * @2018年1月16日 上午10:07:14
 */
public interface ISmsGetaway extends IBaseService<SmsHistory> {

	/**
	 * 向短信网关发送短信
	 * @author andy_hulibo@163.com
	 * @2018年1月16日 上午10:08:15
	 * @param sms
	 */
	public void send(SmsHistory sms);
}
