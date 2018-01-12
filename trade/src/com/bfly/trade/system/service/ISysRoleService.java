package com.bfly.trade.system.service;

import com.bfly.trade.base.service.IBaseService;
import com.bfly.trade.system.entity.SysRole;

/**
 * 系统角色
 * @author 胡礼波-Andy
 * @2016年9月23日下午5:27:12
 */
public interface ISysRoleService extends IBaseService<SysRole> {
	
	/**
	 * 修改角色状态
	 * @author 胡礼波-Andy
	 * @2015年12月8日下午4:23:53
	 * @param roleId
	 * @return
	 */
	public boolean editDisabled(int roleId);
	
	/**
	 * 解除用户与角色之间的绑定
	 * @author 胡礼波-Andy
	 * @2015年12月10日上午10:39:49
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public boolean editUnbindUserRole(int userId,int roleId);
	
	/**
	 * 用户授权
	 * @author 胡礼波-Andy
	 * @2015年12月14日下午12:16:33
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public boolean editBindUserRole(int userId,int roleId);
	
	/**
	 * 角色和菜单绑定
	 * @author 胡礼波-Andy
	 * @2015年12月14日上午10:55:17
	 * @param menuId
	 * @param roleId
	 * @return
	 */
	public boolean editbindMenuRole(int menuId,int roleId);
	
	/**
	 * 角色和菜单解绑
	 * @author 胡礼波-Andy
	 * @2015年12月14日上午11:07:23
	 * @param menuId
	 * @param roleId
	 * @return
	 */
	public boolean editUnbindMenuRole(int menuId,int roleId);
}
