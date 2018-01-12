package com.bfly.trade.system.mapper;

import org.apache.ibatis.annotations.Param;

import com.bfly.trade.base.mapper.BaseMapper;
import com.bfly.trade.system.entity.SysRole;

/**
 * 系统角色数据层
 * @author 胡礼波-Andy
 * @2016年9月23日下午5:35:38
 */
public interface SysRoleMapper extends BaseMapper<SysRole>{

	/**
	 * 角色授权
	 * @author 胡礼波-Andy
	 * @2015年12月14日上午10:57:23
	 * @param menuId
	 * @param roleId
	 * @return
	 */
	public int saveMenuRole(@Param("menuId") int menuId,@Param("roleId")int roleId);
	
	/**
	 * 角色回收授权
	 * @author 胡礼波-Andy
	 * @2015年12月14日上午11:07:56
	 * @param menuId
	 * @param roleId
	 * @return
	 */
	public int delMenuRole(@Param("menuId")int menuId,@Param("roleId")int roleId);
	
	/**
	 * 查询角色是否有该菜单权限
	 * @author 胡礼波-Andy
	 * @2015年12月14日上午11:39:56
	 * @param menuId
	 * @param roleId
	 */
	public int getShipByMenu(@Param("menuId")int menuId,@Param("roleId")int roleId);
	
	/**
	 * 用户授权
	 * @author 胡礼波-Andy
	 * @2015年12月14日上午10:57:23
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public int saveUserRole(@Param("userId")int userId,@Param("roleId")int roleId);
	
	/**
	 * 回收用户角色
	 * @author 胡礼波-Andy
	 * @2015年12月14日上午11:07:56
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public int delUserRole(@Param("userId")int userId,@Param("roleId")int roleId);
	
	/**
	 * 查询用户是否有该角色权限
	 * @author 胡礼波-Andy
	 * @2015年12月14日上午11:39:56
	 * @param userId
	 * @param roleId
	 */
	public int getShipByUser(@Param("userId")int userId,@Param("roleId")int roleId);
}
