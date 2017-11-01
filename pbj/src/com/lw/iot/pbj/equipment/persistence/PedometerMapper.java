package com.lw.iot.pbj.equipment.persistence;

import org.apache.ibatis.annotations.Param;

import com.lw.iot.pbj.core.base.persistence.BaseMapper;
import com.lw.iot.pbj.equipment.entity.Pedometer;

/**
 * 脚环计步数据接口
 * @author andy_hulibo@163.com
 * @2017年10月31日 下午3:30:20
 */
public interface PedometerMapper extends BaseMapper<Pedometer> {

	/**
	 * 根据设备号查找设备
	 * @author andy_hulibo@163.com
	 * @2017年10月31日 下午3:37:58
	 * @param serialNo 设备编号
	 * @return
	 */
	public Pedometer getPedometer(@Param("serialNo") String serialNo);
}
