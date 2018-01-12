package com.bfly.trade.system.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bfly.trade.annotation.ActionModel;
import com.bfly.trade.base.service.impl.BaseServiceImpl;
import com.bfly.trade.system.entity.SysMenu;
import com.bfly.trade.system.mapper.SysMenuMapper;
import com.bfly.trade.system.service.ISysMenuService;

@Service("MenuServiceImpl")
@Transactional(propagation=Propagation.SUPPORTS)
public class SysMenuServiceImpl extends BaseServiceImpl<SysMenu> implements ISysMenuService {

	@Autowired
	private SysMenuMapper menuMapper;
	
	@Override
	@Transactional
	@ActionModel("删除菜单")
	public int del(Integer... ids) {
		if(ids==null || ids.length==0)
		{
			return 0;
		}
		Map<String,Object> params=null;
		for (Integer parentId : ids) {
			params=new HashMap<String, Object>();
			params.put("parentId", parentId);
			List<SysMenu> subList=menuMapper.getList(params);				//子节点
			if(subList!=null)
				{
					Integer subIds[]=new Integer[subList.size()];
					for (int i=0;i<subList.size();i++) {
						subIds[i]=subList.get(i).getId();
					}
					del(subIds);												//级联删除
				}
		}
		return menuMapper.delById(ids);
	}

	@Override
	public List<SysMenu> getList() {
		List<SysMenu> list=menuMapper.getList(null);
		Collections.sort(list);
		return list;
	}



	@Override
	public List<SysMenu> getMenusByRole(int roleId) {
		return menuMapper.getSysMenuByRole(roleId);
	}

	@Override
	public List<SysMenu> getMenusByUser(int userId) {
		return menuMapper.getSysMenusByUser(userId);
	}
}
