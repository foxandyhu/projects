package com.lw.iot.pbj.chicken.service.impl;


import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.lw.iot.pbj.chicken.entity.Chicken;
import com.lw.iot.pbj.chicken.persistence.ChickenMapper;
import com.lw.iot.pbj.chicken.service.IChickenService;
import com.lw.iot.pbj.common.enums.SysEnum;
import com.lw.iot.pbj.core.annotation.ActionModel;
import com.lw.iot.pbj.core.base.service.impl.BaseServiceImpl;
import com.lw.iot.pbj.equipment.entity.Pedometer;
import com.lw.iot.pbj.equipment.service.IPedometerService;

/**
 * 鸡只数据业务实现类
 * @author sunships
 * @date 2018年1月10日下午3:21:42
 */
@Service("ChickenServiceImpl")
@Transactional
@ActionModel(description="鸡只数据")
public class ChickenServiceImpl extends BaseServiceImpl<Chicken> implements IChickenService{

	@Autowired
	private ChickenMapper mapper;
	@Autowired
	private IPedometerService pedometerService;
	
	@Override
	public Chicken getChicken(String serialNo) {
		return mapper.getChicken(serialNo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@ActionModel(description="删除鸡只")
	public void del(String serialNo) {
		Chicken chicken=getChicken(serialNo);
		mapper.delById(chicken.getId());
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	@ActionModel(description="新增鸡只")
	public int save(Chicken chicken) {
		Assert.notNull(chicken,"Chicken is null object");
		Assert.hasText(chicken.getSerialNo(),"the chicken serial no is empty");
		Assert.notNull(chicken.getWearTime(),"Chicken pedometer wearTime is empty");
		Assert.hasText(chicken.getBreeds(),"the breeds is empty");
		Assert.isTrue(chicken.getStatus()!=0,"the status is incorrect");
		Assert.hasText(chicken.getFarm(),"the farm is empty");
		Assert.hasText(chicken.getRemark(),"the introduction is empty");
		
		Pedometer p = pedometerService.getPedometer(chicken.getSerialNo());
		Assert.isTrue(p!=null,"the pedometer serial no is not exists");
		
		Chicken ck=getChicken(chicken.getSerialNo());
		Assert.isTrue(ck==null,"the chicken serial no is already exists");
		return mapper.save(chicken);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	@ActionModel(description="修改鸡只")
	public int edit(Chicken chicken) {
		Assert.notNull(chicken,"Chicken is null object");
		Assert.notNull(chicken.getId(),"Chicken id is empty");
		Assert.hasText(chicken.getSerialNo(),"the chicken serial no is empty");
		Assert.notNull(chicken.getWearTime(),"Chicken pedometer wearTime is empty");
		Assert.hasText(chicken.getBreeds(),"the breeds is empty");
		Assert.isTrue(chicken.getStatus()!=0,"the status is incorrect");
		Assert.hasText(chicken.getFarm(),"the farm is empty");
		Assert.hasText(chicken.getRemark(),"the introduction is empty");
		return mapper.editById(chicken);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	@ActionModel(description="标记鸡只为已出栏")
	public void updateStatus(String ids) {
		Date now = new Date();
		String[] id = ids.split(",");
		for(int i =0;i<id.length;i++){
			Chicken chicken = get(Integer.valueOf(id[i]));
			chicken.setSellTime(now);
			chicken.setStatus(SysEnum.ChickenStatus.SELLED.getKey());
			mapper.editById(chicken);
		}
		
	}
}
