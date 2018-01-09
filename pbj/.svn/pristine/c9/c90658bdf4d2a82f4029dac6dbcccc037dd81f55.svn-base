package com.lw.iot.pbj.equipment.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lw.iot.pbj.core.annotation.ActionModel;
import com.lw.iot.pbj.core.base.service.impl.BaseServiceImpl;
import com.lw.iot.pbj.equipment.entity.Pedometer;
import com.lw.iot.pbj.equipment.persistence.PedometerMapper;
import com.lw.iot.pbj.equipment.service.IPedometerService;

/**
 * 脚环计步器业务实现类
 * @author andy_hulibo@163.com
 * @2017年10月31日 下午3:28:27
 */
@Service("PedometerServiceImpl")
@Transactional(rollbackFor=Exception.class)
@ActionModel(description="脚环计步器")
public class PedometerServiceImpl extends BaseServiceImpl<Pedometer> implements IPedometerService {

	@Autowired
	private PedometerMapper pedometerMapper;

	@Override
	public Pedometer getPedometer(String serialNo) {
		return pedometerMapper.getPedometer(serialNo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@ActionModel(description="删除脚环计步器设备")
	public void del(String serialNo) {
		Pedometer pedometer=getPedometer(serialNo);
		pedometerMapper.delById(pedometer.getId());
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@ActionModel(description="新增脚环计步器设备")
	public int save(Pedometer pedometer) {
		Assert.notNull(pedometer,"Pedometer is null object");
		Assert.hasText(pedometer.getSerialNo(),"the pedometer serial no is empty");
		Pedometer pd=getPedometer(pedometer.getSerialNo());
		
		Assert.isTrue(pd==null,"the pedometer serial no is already exists");
		return pedometerMapper.save(pedometer);
	}
}
