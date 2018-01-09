package com.lw.iot.pbj.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lw.iot.pbj.core.annotation.ActionModel;
import com.lw.iot.pbj.core.base.service.impl.BaseServiceImpl;
import com.lw.iot.pbj.system.entity.SysConfig;
import com.lw.iot.pbj.system.persistence.SysConfigMapper;
import com.lw.iot.pbj.system.service.ISysConfigService;

/**
 * 系统配置信息业务实现
 * @author andy_hulibo@163.com
 * @2017年10月31日 下午2:37:03
 */
@Service
@ActionModel(description="系统配置信息")
@Transactional(propagation=Propagation.SUPPORTS,rollbackFor=Exception.class)
public class SysConfigServiceImpl extends BaseServiceImpl<SysConfig> implements ISysConfigService {

	@Autowired
	private SysConfigMapper configMapper;
	
	/**
	 * 存在则修改 否则新增
	 */
	@Override
	@Transactional(rollbackFor=Exception.class)
	@ActionModel(description="设置系统配置信息")
	public int save(SysConfig config) {
		SysConfig sc=getSysConfig();
		if(sc==null)
		{
			return configMapper.save(config);
		}
		return configMapper.editById(config);
	}

	@Override
	public SysConfig getSysConfig() {
		return configMapper.getById(0);
	}
}
