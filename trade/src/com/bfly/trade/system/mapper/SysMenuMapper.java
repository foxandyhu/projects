package com.bfly.trade.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bfly.trade.base.mapper.BaseMapper;
import com.bfly.trade.system.entity.SysMenu;

/**
 * 系统菜单数据接口
 * @author 胡礼波-Andy
 * @2016年9月23日下午5:29:58
 */
public interface SysMenuMapper extends BaseMapper<SysMenu> {
	
	/**
	 * 查找角色赋予的菜单权限
	 * @author 胡礼波-Andy
	 * @2016年9月26日下午6:50:52
	 * @param roleId
	 * @return
	 */
	public List<SysMenu> getSysMenuByRole(@Param("roleId") int roleId);
	
	/**
	 * 查找用户的菜单功能
	 * @author 胡礼波-Andy
	 * @2016年9月27日下午5:37:26
	 * @param userId
	 * @return
	 */
	public List<SysMenu> getSysMenusByUser(@Param("userId") int userId);
}
