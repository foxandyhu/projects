package com.lw.iot.pbj.users.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lw.iot.pbj.common.json.JsonUtil;
import com.lw.iot.pbj.common.page.Pager;
import com.lw.iot.pbj.common.util.DataConvertUtils;
import com.lw.iot.pbj.common.util.ResponseUtil;
import com.lw.iot.pbj.core.base.action.BaseAction;
import com.lw.iot.pbj.core.encoder.Md5PwdEncoder;
import com.lw.iot.pbj.users.entity.Users;
import com.lw.iot.pbj.users.service.IUserService;


/**
 * 后台管理用户Action
 * @author 胡礼波-Andy
 * @2014年11月10日上午10:30:23
 *
 */
@Controller("UserAction")
@RequestMapping(value="/manage/user")
public class UserAction extends BaseAction {

	@Autowired
	private IUserService userService;
	
	/**
	 * 用户列表
	 * @author 胡礼波-Andy
	 * @2014年11月11日下午2:53:53
	 * 
	 * @return
	 */
	@RequestMapping(value="")
	public void list(HttpServletResponse response)
	{
		instantPage(10);
		List<Users> list=userService.getList();
		int total=userService.getCount();
		Pager pager=new Pager(super.getPage(),super.getRows(),total);
		pager.setDatas(list);
		ResponseUtil.writeJson(response, JsonUtil.toJsonStringFilterPropter(pager).toJSONString());
	}
	
	/**
	 * 添加用户
	 * @author 胡礼波-Andy
	 * @2014年11月12日上午11:15:40
	 * @param response
	 * @return
	 */
	@RequestMapping(value="post")
	public String addUsers(HttpServletResponse response,Users user)
	{
		userService.save(user);
		return "redirect:/manage/user.html";
	}
	
	/**
	 * 查看用户
	 * @author 胡礼波-Andy
	 * @2015年1月20日下午4:35:32
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/{userId}")
	public void viewUser(@PathVariable("userId")int userId,HttpServletResponse response)
	{
		Users user=userService.get(userId);
		ResponseUtil.writeJson(response,JsonUtil.toJsonStringFilterPropter(user).toJSONString());
	}
	
	/**
	 * 用户编辑
	 * @author 胡礼波-Andy
	 * @2015年1月20日下午1:54:51
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/edit")
	public String editUsers(HttpServletResponse response,Users user)
	{
		userService.edit(user);
		return null;
	}
	
	/**
	 * 删除系统日志
	 * @author 胡礼波-Andy
	 * @2014年11月12日上午9:36:15
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/del")
	public void delUsers(HttpServletResponse response)
	{
		String dataStr=getRequest().getParameter("data");
		String[] datas=StringUtils.split(dataStr, ",");
		Integer[] ids=DataConvertUtils.convertToInteger(datas);	
		userService.del(ids);
		ResponseUtil.writeJson(response, null);
	}
	
	/**
	 * 修改密码
	 * @author 胡礼波-Andy
	 * @2014年11月12日下午12:11:56
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/editpwd")
	public void editPwd(HttpServletResponse response)
	{
		HttpServletRequest request=getRequest();
		Users user=getLoginedUser();
		String oldPwd=request.getParameter("oldPassword");
		//原密码不正确
		if(!new Md5PwdEncoder().isPasswordValid(user.getPassword(),oldPwd,user.getSalt()))
		{
			throw new RuntimeException("原始密码不正确!");
		}
		String password=request.getParameter("password");
		Assert.hasText(password,"新密码不能为空!");
		userService.editPassword(user.getId(), password);
	}

	/**
	 * 修改帐号状态
	 * @author 胡礼波-Andy
	 * @2014年11月12日下午1:19:25
	 * @return
	 */
	@RequestMapping(value="/editenable/{userId}")
	public void editUserEnable(HttpServletResponse response,@PathVariable("userId")int userId)
	{
		if(getLoginedUser().getId()==userId)
		{
			throw new RuntimeException("不能修改自己的帐号状态!");
		}
		userService.editUserEnable(userId);
		ResponseUtil.writeJson(response,"");
	}
	
	/**
	 * 加载未分配指定角色的用户列表
	 * @author 胡礼波-Andy
	 * @2015年12月14日下午3:45:38
	 * @return
	 */
	@RequestMapping(value="/role/unassign")
	public void loadUsersByStoreNotRole(HttpServletResponse response)
	{
		instantPage(5);
		List<Users> users=userService.getUsersForUnassignRole();
		int total=userService.getCountUsersForUnassignRole();
		Pager pager=new Pager(super.getPage(),getRows(),total);
		pager.setDatas(users);
		ResponseUtil.writeJson(response, JsonUtil.toJsonStringFilterPropter(pager).toJSONString());
	}
	
	/**
	 * 加载角色下的用户信息
	 * @author 胡礼波-Andy
	 * @2015年12月9日下午6:35:15
	 * @return
	 */
	@RequestMapping(value="/role/{roleId}")
	public void loadUserByRole(@PathVariable("roleId") final int roleId,HttpServletResponse response)
	{
		List<Users> users=userService.getUsersByRole(roleId);
		JSONArray array=new JSONArray();
		JSONObject json=null;
		for (Users u:users) {
			json=new JSONObject();
			json.put("id",u.getId());
			json.put("name", u.getName());
			json.put("enable",u.isEnable());
			json.put("userName",u.getUserName());
			array.add(json);
		}
		ResponseUtil.writeJson(response, array.toJSONString());
	}
}
