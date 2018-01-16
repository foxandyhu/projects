package com.bfly.trade.message.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.trade.base.service.impl.BaseServiceImpl;
import com.bfly.trade.message.entity.SmsHistory;
import com.bfly.trade.message.service.ISmsGetaway;

@Transactional
@Service("DefaultSmsGetawayServiceImpl")
public class DefaultSmsGetawayServiceImpl extends BaseServiceImpl<SmsHistory> implements ISmsGetaway {

	@Override
	public void send(SmsHistory sms) {
		
	}

}
