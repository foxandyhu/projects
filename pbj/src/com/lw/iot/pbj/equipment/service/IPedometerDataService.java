package com.lw.iot.pbj.equipment.service;

import java.util.Collection;
import java.util.Date;

import com.lw.iot.pbj.core.base.service.IBaseService;
import com.lw.iot.pbj.equipment.entity.PedometerData;

/**
 * 脚环计步器数据业务接口
 * @author andy_hulibo@163.com
 * @2017年10月31日 下午3:27:31
 */
public interface IPedometerDataService extends IBaseService<PedometerData> {

	/**
	 * 批量保存脚环计步器数据
	 * @author andy_hulibo@163.com
	 * @2017年11月1日 下午1:36:07
	 * @param list 数据集合
	 */
	public void save(Collection<PedometerData> list);
	
	/**
	 * 根据脚环计步器编号查找计步器数据-一个脚环一天一条数据
	 * @author andy_hulibo@163.com
	 * @2017年11月1日 上午11:38:52
	 * @param serialNo 设备编号
	 * @param date 日期 例如:2017-11-1
	 * @return
	 */
	public PedometerData getPedometerData(String serialNo,Date date);
}
