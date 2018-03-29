package com.lw.iot.pbj.chicken.persistence;

import org.apache.ibatis.annotations.Param;

import com.lw.iot.pbj.chicken.entity.Chicken;
import com.lw.iot.pbj.core.base.persistence.BaseMapper;

/**
 * 鸡只数据数据接口
 * @author sunships
 * @date 2018年1月10日下午3:23:22
 */
public interface ChickenMapper extends BaseMapper<Chicken> {

	/**
	 * 根据鸡只编号查找鸡只
	 * @author sunships
	 * @date 2018年1月10日下午3:39:12
	 * @param serialNo
	 * @return
	 */
	public Chicken getChicken(@Param("serialNo") String serialNo);
}
