package com.bfly.trade.message.service.impl;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.trade.annotation.ActionModel;
import com.bfly.trade.base.service.impl.BaseServiceImpl;
import com.bfly.trade.message.entity.SmsHistory;
import com.bfly.trade.message.mapper.SmsHistoryMapper;
import com.bfly.trade.message.service.ISmsGetaway;
import com.bfly.trade.message.service.ISmsHistoryService;

@Service("SmsHistoryServiceImpl")
@Transactional(propagation=Propagation.SUPPORTS)
@ActionModel("短信记录")
public class SmsHistoryServiceImpl extends BaseServiceImpl<SmsHistory> implements ISmsHistoryService{

	private Logger logger=Logger.getLogger(SmsHistoryServiceImpl.class);
	@Autowired @Qualifier("workTaskExecutor")
	private ThreadPoolTaskExecutor workTaskExecutor;
	@Autowired
	private SmsHistoryMapper smsMapper;
	@Autowired
	private ISmsGetaway smsGetaway;
	
	@Override
	@Transactional
	@ActionModel("发送保存短信")
	public boolean sendAndSaveSms(String phone,String content,int status)
	{
		final AtomicInteger sendCount=new AtomicInteger(1);
		
		Future<Boolean>  future=workTaskExecutor.submit(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception
			{
				SmsHistory sms=new SmsHistory();
				sms.setContent(content);
				sms.setStatus(status);
				sms.setPhone(phone);
				sms.setTime(new Date());
				sms.setTotal(0);
				
				smsMapper.save(sms);
				do
				{
					boolean flag=false;
					try
					{
						smsGetaway.send(sms);
						flag=true;
					}
					catch(Exception e)
					{
						if(sendCount.get()>=5){break;}
						logger.error("短信发送失败,系统正尝试第"+(sendCount.incrementAndGet())+"次发送.",e);
					}
					sms.setTotal(sms.getTotal()+1);				//发送次数+1
					sms.setTime(new Date());
					if(flag)
					{
						sms.setStatus(SmsHistory.SUCCESS);		//发送成功跳出循环
						smsMapper.editById(sms);
						return true;
					}
					sms.setStatus(SmsHistory.FAILED);
					smsMapper.editById(sms);
				}while(sendCount.get()<=5);
				return false;
			}
		});
		try
		{
			return future.get();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

}
