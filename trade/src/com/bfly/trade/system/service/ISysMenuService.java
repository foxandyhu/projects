package com.bfly.trade.system.service;

import java.util.List;

import com.bfly.trade.base.service.IBaseService;
import com.bfly.trade.system.entity.SysMenu;

/**
 * 系统菜单业务接口
 * @author 胡礼波-Andy
 * @2016年9月23日下午5:26:30
 */
public interface ISysMenuService extends IBaseService<SysMenu> {

	/**
	 * 根据角色Id查询该角色下的所有菜单功能
	 * @author 胡礼波-Andy
	 * @2015年12月14日上午10:28:52
	 * @param roleId
	 * @return
	 */
	public List<SysMenu> getMenusByRole(int roleId);
	
	/**
	 * 加载用户的菜单功能
	 * @author 胡礼波-Andy
	 * @2016年9月27日下午5:36:30
	 * @param userId
	 * @return
	 */
	public List<SysMenu> getMenusByUser(int userId);
}
