package com.bfly.trade.system.action;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bfly.trade.base.action.BaseAction;
import com.bfly.trade.system.entity.SysRole;
import com.bfly.trade.system.service.ISysRoleService;
import com.bfly.trade.util.JsonUtil;
import com.bfly.trade.util.ResponseUtil;

/**
 * 系统角色Action
 * @author 胡礼波-Andy
 * @2015年11月10日下午1:41:17
 */
@Controller("RoleAction")
@RequestMapping(value="/manage/sys/role")
public class RoleAction extends BaseAction {
	
	@Autowired
	private ISysRoleService roleService;
	
	/**
	 * 加载系统角色
	 * @author 胡礼波-Andy
	 * @2015年11月10日下午1:45:09
	 * @return
	 */
	@RequestMapping(value="")
	public void loadRole(HttpServletResponse response)
	{
		instantPage();
		List<SysRole> list=roleService.getList();
		JSONArray array=JsonUtil.toJsonStringFilterPropterForArray(list);
		
		JSONObject baseJson=new JSONObject();
		baseJson.put("name","系统角色");
		baseJson.put("id",0);
		baseJson.put("open",true);
		baseJson.put("children", array);
		ResponseUtil.writeJson(response, baseJson.toJSONString());
	}
	
	/**
	 * 添加角色
	 * @author 胡礼波-Andy
	 * @2015年12月8日下午4:51:40
	 * @return
	 */
	@RequestMapping(value="/post")
	public void addRole(HttpServletResponse response)
	{
		SysRole roles=new SysRole();
		roles.setName("新角色");
		roles.setEnable(true);
		roleService.save(roles);
		ResponseUtil.writeJson(response,"");
	}
	
	/**
	 * 
	 * @author 胡礼波-Andy
	 * @2015年12月8日下午5:19:56
	 * @return
	 */
	@RequestMapping(value="/del/{roleId}")
	public void delRole(HttpServletResponse response,@PathVariable("roleId")int roleId)
	{
		roleService.del(roleId);
		ResponseUtil.writeJson(response,"");
	}
	
	/**
	 * 编辑角色
	 * @author 胡礼波-Andy
	 * @2015年12月8日下午5:51:57
	 * @return
	 */
	@RequestMapping(value="/edit")
	public void editRole(HttpServletResponse response,SysRole role)
	{
		roleService.edit(role);
		ResponseUtil.writeJson(response,"");
	}
	
	
	/**
	 * 授权用户的角色
	 * @author 胡礼波-Andy
	 * @2015年12月10日上午10:35:44
	 * @return
	 */
	@RequestMapping(value="/bind/users/{userId}-{roleId}")
	public void bindUserForRole(HttpServletResponse response,@PathVariable("userId")int userId,@PathVariable("roleId")int roleId)
	{
		roleService.editBindUserRole(userId, roleId);
		ResponseUtil.writeJson(response,"");
	}
	
	/**
	 * 解除用户的角色
	 * @author 胡礼波-Andy
	 * @2015年12月10日上午10:35:44
	 * @return
	 */
	@RequestMapping(value="/unbind/users/{userId}-{roleId}")
	public void unbindUserForRole(HttpServletResponse response,@PathVariable("userId")int userId,@PathVariable("roleId")int roleId)
	{
		roleService.editUnbindUserRole(userId, roleId);
		ResponseUtil.writeJson(response,"");
	}
	
	/**
	 * 角色和菜单绑定
	 * @author 胡礼波-Andy
	 * @2015年12月14日上午10:54:31
	 * @return
	 */
	@RequestMapping(value="/bind/menus/{menuId}-{roleId}")
	public void bindRoleMenu(HttpServletResponse response,@PathVariable("roleId")int roleId,@PathVariable("menuId")int menuId)
	{
		roleService.editbindMenuRole(menuId, roleId);
		ResponseUtil.writeJson(response,"");
	}
	
	/**
	 * 角色和菜单绑定
	 * @author 胡礼波-Andy
	 * @2015年12月14日上午10:54:31
	 * @return
	 */
	@RequestMapping(value="/unbind/menus/{menuId}-{roleId}")
	public void unBindRoleMenu(HttpServletResponse response,@PathVariable("roleId")int roleId,@PathVariable("menuId")int menuId)
	{
		roleService.editUnbindMenuRole(menuId, roleId);
		ResponseUtil.writeJson(response,"");
	}
}
