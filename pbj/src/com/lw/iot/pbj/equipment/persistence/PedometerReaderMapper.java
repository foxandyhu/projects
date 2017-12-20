package com.lw.iot.pbj.equipment.persistence;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.lw.iot.pbj.core.base.persistence.BaseMapper;
import com.lw.iot.pbj.equipment.entity.PedometerReader;

/**
 * 计步器阅读器数据接口
 * @author andy_hulibo@163.com
 * @2017年10月31日 下午3:30:20
 */
public interface PedometerReaderMapper extends BaseMapper<PedometerReader> {

	/**
	 * 根据设备号查找设备
	 * @author andy_hulibo@163.com
	 * @2017年10月31日 下午3:37:58
	 * @param serialNo 设备编号
	 * @return
	 */
	public PedometerReader getPedometerReader(@Param("serialNo") String serialNo);
	
	/**
	 * 修改设备通信信息
	 * @author andy_hulibo@163.com
	 * @2017年10月31日 下午5:06:39
	 * @param serialNo 设备编号
	 * @param date 通信时间
	 * @param online 是否在线
	 * @return
	 */
	public int editCommInfo(@Param("serialNo") String serialNo, @Param("date")Date date,@Param("online") boolean online);
	
	/**
	 * 更新设备GPS坐标
	 * @author andy_hulibo@163.com
	 * @2017年12月20日 上午10:18:21
	 * @param serialNo
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	public int editCommLocation(@Param("serialNo") String serialNo,@Param("longitude") String longitude,@Param("latitude") String latitude);
}
