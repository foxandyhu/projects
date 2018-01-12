package com.bfly.trade.system.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.bfly.trade.annotation.ActionModel;
import com.bfly.trade.base.service.impl.BaseServiceImpl;
import com.bfly.trade.system.entity.SysMenu;
import com.bfly.trade.system.entity.SysRole;
import com.bfly.trade.system.mapper.SysRoleMapper;
import com.bfly.trade.system.service.ISysMenuService;
import com.bfly.trade.system.service.ISysRoleService;
import com.bfly.trade.users.entity.Users;
import com.bfly.trade.users.service.IUsersService;

@Service("RoleServiceImpl")
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements ISysRoleService {

	@Autowired
	private SysRoleMapper roleMapper;
	@Autowired
	private IUsersService usersService;
	@Autowired
	private ISysMenuService menuService;

	@Override
	@Transactional
	@ActionModel("修改角色状态")
	public boolean editDisabled(int roleId){
		SysRole role=roleMapper.getById(roleId);
		Assert.notNull(role,"不存在该角色!");
		role.setEnable(!role.isEnable());
		return roleMapper.editById(role)>0;
	}


	@Override
	@Transactional
	@ActionModel("删除角色")
	public int del(Integer... id) {
		int count=0;
		if(id!=null)
		{
			List<Users> users=null;
			List<SysMenu> menus=null;
			SysRole role=null;
			for (Integer roleId : id) {
				role=roleMapper.getById(roleId);
				if(role!=null)
				{
					users=usersService.getUsersByRole(roleId);
					if(users!=null)
					{
						for (Users user : users) {						//解除角色与用户之间的绑定
							editUnbindUserRole(user.getId(), roleId);
						}
					}
					menus=menuService.getMenusByRole(roleId);
					if(menus!=null)
					{
						for (SysMenu sysMenu : menus) {
							editUnbindMenuRole(sysMenu.getId(), roleId);
						}
					}
					roleMapper.delById(roleId);							//删除角色
				}
			}
		}
		return count;
	}

	@Override
	@Transactional
	@ActionModel("回收用户角色")
	public boolean editUnbindUserRole(int userId, int roleId) {
		return roleMapper.delUserRole(userId, roleId)>0;
	}
	
	@Override
	@Transactional
	@ActionModel("用户授权")
	public boolean editBindUserRole(int userId, int roleId) {
		SysRole role=get(roleId);
		Assert.notNull(role,"该角色不存在!");
		int count=roleMapper.getShipByUser(userId, roleId);
		if(count>0)
		{
			return true;
		}
		return roleMapper.saveUserRole(userId, roleId)>0;
	}

	@Override
	@Transactional
	@ActionModel("角色授权")
	public boolean editbindMenuRole(int menuId, int roleId) {
		SysRole role=get(roleId);
		Assert.notNull(role,"该角色不存在!");
		int count=roleMapper.getShipByMenu(menuId, roleId);
		if(count>0)
		{
			return true;
		}
		return roleMapper.saveMenuRole(menuId, roleId)>0;
	}

	@Override
	@Transactional
	@ActionModel("角色回收权限")
	public boolean editUnbindMenuRole(int menuId, int roleId) {
		return roleMapper.delMenuRole(menuId, roleId)>0;
	}
}
