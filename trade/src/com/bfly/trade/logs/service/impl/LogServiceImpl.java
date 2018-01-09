package com.bfly.trade.logs.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.trade.annotation.ActionModel;
import com.bfly.trade.base.service.impl.BaseServiceImpl;
import com.bfly.trade.logs.entity.SysLog;
import com.bfly.trade.logs.service.ILogService;

/**
 * 
 * @author andy_hulibo@163.com
 * 2018年1月8日下午3:39:35
 */
@Service("LogServiceImpl")
@Transactional(propagation=Propagation.SUPPORTS,rollbackFor=Throwable.class)
@ActionModel("日志操作")
public class LogServiceImpl extends BaseServiceImpl<SysLog> implements ILogService {

}
