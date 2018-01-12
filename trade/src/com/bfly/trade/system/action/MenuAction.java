package com.bfly.trade.system.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bfly.trade.base.action.BaseAction;
import com.bfly.trade.system.entity.SysMenu;
import com.bfly.trade.system.service.ISysMenuService;
import com.bfly.trade.util.JsonUtil;
import com.bfly.trade.util.ResponseUtil;

/**
 * 系统菜单Action
 * @author 胡礼波-Andy
 * @2015年11月10日下午1:41:17
 */
@Controller("MenuAction")
@RequestMapping(value="/manage/sys/menu")
public class MenuAction extends BaseAction {
	
	@Autowired
	private ISysMenuService menuService;
	
	/**
	 * 加载系统菜单
	 * @author 胡礼波-Andy
	 * @2015年11月10日下午1:45:09
	 * @return
	 */
	@RequestMapping(value="")
	public void loadSysMenu(HttpServletResponse response)
	{
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("type",SysMenu.SYS_MENU);
		params.put("parentId",0);
		List<SysMenu> list=menuService.getList(params);
		
		Collections.sort(list);
		JSONArray array=JsonUtil.toJsonStringFilterPropterForArray(list);
		JSONObject jobj=new JSONObject();
		jobj.put("id",0);
		jobj.put("name","系统菜单");
		jobj.put("open",true);
		array.add(0,jobj);
		ResponseUtil.writeJson(response,array.toJSONString());
	}
	
	
	/**
	 * 删除系统菜单
	 * @author 胡礼波-Andy
	 * @2015年11月10日下午1:48:01
	 * @param menuId
	 * @return
	 */
	@RequestMapping(value="/del/{menuId}")
	public void delMenu(HttpServletResponse response,@PathVariable("menuId")int menuId)
	{
		menuService.del(menuId);
		ResponseUtil.writeJson(response, "");
	}
	
	/**
	 * 添加菜单
	 * @author 胡礼波-Andy
	 * @2015年11月10日下午1:49:54
	 * @param response
	 * @param menu
	 * @return
	 */
	@RequestMapping(value="/post")
	public void addMenu(HttpServletResponse response,SysMenu menu)
	{
		if(menu!=null)
		{
			if(menu.getType()==0)
			{
				menu.setType(SysMenu.SYS_MENU);
			}
		}
		menuService.save(menu);
		ResponseUtil.writeJson(response, "");
	}
	
	/**
	 * 编辑菜单
	 * @author 胡礼波-Andy
	 * @2015年11月10日下午1:50:44
	 * @param response
	 * @param menu
	 * @return
	 */
	@RequestMapping(value="/edit")
	public void editMenu(HttpServletResponse response,SysMenu menu)
	{
		menuService.edit(menu);
		ResponseUtil.writeJson(response, "");
	}
	
	/**
	 * 根据角色加载角色对应的权限
	 * @author 胡礼波-Andy
	 * @2015年12月10日下午5:59:30
	 * @return
	 */
	@RequestMapping(value="/role/{roleId}")
	public void loadMenu(HttpServletResponse response, @PathVariable("roleId")int roleId)
	{
		List<SysMenu> menus=menuService.getList();
		List<SysMenu> roleMenus=menuService.getMenusByRole(roleId);
		JSONArray array=new JSONArray();
		if(menus!=null)
		{
			array=JsonUtil.toJsonStringFilterPropterForArray(menus,"url","remark","children");
			JSONObject json=null;
			for (int i=0;i<array.size();i++)
			{
				json=array.getJSONObject(i);
				json.put("roleId",roleId);
				if(roleMenus!=null)
				{
					for (SysMenu m:roleMenus)
					{
						if(m.getId()==json.getIntValue("id"))
						{
							json.put("checked",true);
							break;
						}
					}
				}
			}
		}
		ResponseUtil.writeJson(response,array.toJSONString());
	}
}
