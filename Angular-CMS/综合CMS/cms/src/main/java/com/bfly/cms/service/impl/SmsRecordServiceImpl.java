package com.bfly.cms.service.impl;

import com.bfly.cms.entity.SmsProvider;
import com.bfly.cms.entity.SmsRecord;
import com.bfly.cms.service.ISmsRecordService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/18 11:31
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class SmsRecordServiceImpl extends BaseServiceImpl<SmsRecord, Integer> implements ISmsRecordService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean resend(int recordId) {
        SmsRecord record = get(recordId);
        Assert.notNull(record, "短信记录不存在!");

        SmsProvider provider = record.getProvider();
        Assert.notNull(provider, "该记录没有对应的服务商信息!");
        //此处应该调用服务商API发送短信

        //修改发送次数
        record.setCount(record.getCount() + 1);
        record.setSendTime(new Date());
        return edit(record);
    }
}
