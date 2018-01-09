package com.lw.iot.pbj.equipment.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lw.iot.pbj.core.annotation.ActionModel;
import com.lw.iot.pbj.core.annotation.LogRecord;
import com.lw.iot.pbj.core.annotation.RecordType;
import com.lw.iot.pbj.core.base.service.impl.BaseServiceImpl;
import com.lw.iot.pbj.equipment.entity.PedometerReader;
import com.lw.iot.pbj.equipment.persistence.PedometerReaderMapper;
import com.lw.iot.pbj.equipment.service.IPedometerReaderService;

/**
 * 计步器阅读器业务实现类
 * @author andy_hulibo@163.com
 * @2017年10月31日 下午3:28:27
 */
@Service("PedometerReaderServiceImpl")
@Transactional(rollbackFor=Exception.class)
@ActionModel(description="计步器阅读器")
public class PedometerReaderServiceImpl extends BaseServiceImpl<PedometerReader> implements IPedometerReaderService {

	@Autowired
	private PedometerReaderMapper pedometerReaderMapper;

	@Override
	public PedometerReader getPedometerReader(String serialNo) {
		return pedometerReaderMapper.getPedometerReader(serialNo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@ActionModel(description="删除阅读器设备")
	public void del(String serialNo) {
		PedometerReader reader=getPedometerReader(serialNo);
		pedometerReaderMapper.delById(reader.getId());
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@ActionModel(description="修改阅读器设备状态")
	public void enable(String serialNo, boolean enable) {
		PedometerReader reader=getPedometerReader(serialNo);
		reader.setEnable(enable);
		pedometerReaderMapper.editById(reader);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@ActionModel(description="新增阅读器设备")
	public int save(PedometerReader reader) {
		Assert.notNull(reader,"Pedometer Reader is null object");
		Assert.hasText(reader.getSerialNo(),"the pedometer reader serial no is empty");
		Assert.hasText(reader.getAddress(),"the pedometer reader installation location is empty");
		PedometerReader rd=getPedometerReader(reader.getSerialNo());
		
		Assert.isTrue(rd==null,"the pedometer reader serial no is already exists");
		return pedometerReaderMapper.save(reader);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	@ActionModel(description="修改阅读器设备")
	public int edit(PedometerReader reader) {
		PedometerReader rd=getPedometerReader(reader.getSerialNo());
		rd.setRemark(reader.getRemark());
		rd.setAddress(reader.getAddress());
		rd.setEnable(reader.isEnable());
		return pedometerReaderMapper.editById(rd);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@LogRecord(record=RecordType.IGNORE)
	public void editLastComm(String serialNo, Date date) {
		pedometerReaderMapper.editCommInfo(serialNo, date,true);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@LogRecord(record=RecordType.IGNORE)
	public void editCommLocation(String serialNo, String longitude, String latitude) {
		pedometerReaderMapper.editCommLocation(serialNo, longitude, latitude);
	}
}
