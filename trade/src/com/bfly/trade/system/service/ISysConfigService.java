package com.bfly.trade.system.service;

import com.bfly.trade.base.service.IBaseService;
import com.bfly.trade.system.entity.SysConfig;

/**
 * 系统配置业务接口
 * @author 胡礼波-Andy
 * @2015年5月22日下午5:42:47
 */
public interface ISysConfigService extends IBaseService<SysConfig> {

	/**
	 * 得到系统配置
	 * @author 胡礼波-Andy
	 * @2015年5月22日下午5:51:23
	 * @return
	 */
	public SysConfig getSysConfig();
}
