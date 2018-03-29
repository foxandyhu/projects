package com.lw.iot.pbj.chicken.service;

import com.lw.iot.pbj.chicken.entity.Chicken;
import com.lw.iot.pbj.core.base.service.IBaseService;

/**
 * 鸡只数据业务接口
 * @author sunships
 * @date 2018年1月10日下午3:21:29
 */
public interface IChickenService extends IBaseService<Chicken>{

	/**
	 * 根据鸡只编号查找鸡只
	 * @author sunships
	 * @date 2018年1月10日下午3:37:41
	 * @param serialNo
	 * @return
	 */
	public Chicken getChicken(String serialNo);
	
	/**
	 * 删除鸡只
	 * @author sunships
	 * @date 2018年1月18日下午11:08:54
	 * @param serialNo
	 */
	public void del(String serialNo);
	
	/**
	 * 标记鸡只为出栏
	 * @author sunships
	 * @date 2018年1月22日下午3:35:45
	 * @param ids
	 */
	public void updateStatus(String ids);
}
