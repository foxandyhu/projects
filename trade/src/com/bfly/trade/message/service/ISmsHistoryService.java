package com.bfly.trade.message.service;

import com.bfly.trade.base.service.IBaseService;
import com.bfly.trade.message.entity.SmsHistory;

/**
 * 短信记录业务层
 * @author 胡礼波-Andy
 * @2016年8月30日上午10:30:38
 */
public interface ISmsHistoryService extends IBaseService<SmsHistory> {

	/**
	 * 发送并保存短信记录--默认发送5次
	 * @author 胡礼波
	 * 2017年5月7日上午11:55:08
	 * @param sms
	 */
	public boolean sendAndSaveSms(String phone,String content,int status);
}
