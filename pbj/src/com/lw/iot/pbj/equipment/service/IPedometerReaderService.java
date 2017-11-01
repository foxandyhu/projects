package com.lw.iot.pbj.equipment.service;

import java.util.Date;

import com.lw.iot.pbj.core.base.service.IBaseService;
import com.lw.iot.pbj.equipment.entity.PedometerReader;

/**
 * 计步器阅读器业务接口
 * @author andy_hulibo@163.com
 * @2017年10月31日 下午3:27:31
 */
public interface IPedometerReaderService extends IBaseService<PedometerReader> {

	/**
	 * 根据设备编号查找设备
	 * @author andy_hulibo@163.com
	 * @2017年10月31日 下午3:39:23
	 * @param serialNo
	 * @return
	 */
	public PedometerReader getPedometerReader(String serialNo);
	
	
	/**
	 * 删除计步器阅读器设备
	 * @author andy_hulibo@163.com
	 * @2017年10月31日 下午3:53:38
	 * @param serialNo
	 */
	public void del(String serialNo);
	
	/**
	 * 修改阅读器的状态
	 * @author andy_hulibo@163.com
	 * @2017年10月31日 下午3:57:11
	 * @param serialNo
	 * @param enable
	 */
	public void enable(String serialNo,boolean enable);
	
	/**
	 * 修改设备通信时间
	 * @author andy_hulibo@163.com
	 * @2017年10月31日 下午5:04:22
	 * @param serialNo 设备编号
	 * @param date 通信时间
	 * @param ip 通信ip
	 */
	public void editLastComm(String serialNo,Date date);
}
