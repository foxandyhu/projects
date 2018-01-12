package com.bfly.trade.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.trade.annotation.ActionModel;
import com.bfly.trade.base.service.impl.BaseServiceImpl;
import com.bfly.trade.system.entity.SysConfig;
import com.bfly.trade.system.mapper.SysConfigMapper;
import com.bfly.trade.system.service.ISysConfigService;

@Service
@ActionModel("系统配置信息")
@Transactional(propagation=Propagation.SUPPORTS)
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfig> implements ISysConfigService {

	@Autowired
	private SysConfigMapper configMapper;
	
	@Override
	@Transactional
	@ActionModel("设置系统配置信息")
	public boolean save(SysConfig config) {
		SysConfig sc=getSysConfig();
		if(sc==null)
		{
			return configMapper.save(config)>0;
		}
		return configMapper.editById(config)>0;
	}

	@Override
	public SysConfig getSysConfig() {
		return configMapper.getById(0);
	}
}
