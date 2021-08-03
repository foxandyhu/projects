package com.bfly.manage.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.entity.SysMenu;
import com.bfly.cms.entity.User;
import com.bfly.cms.entity.UserRole;
import com.bfly.cms.service.IUserService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.json.JsonUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseController;
import com.bfly.core.cache.UserRightContainer;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.ActionModel;
import com.bfly.core.security.Login;
import com.octo.captcha.service.CaptchaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

/**
 * 系统管理员用户Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/7 14:11
 */
@RestController
@RequestMapping(value = "/manage/user")
public class UserController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;
    @Autowired
    private CaptchaService captchaService;

    /**
     * 用户登录
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/24 12:46
     */
    @PostMapping(value = "/login")
    @Login(required = false)
    @ActionModel(value = "用户登录", recordLog = false)
    public void login(HttpServletResponse response, HttpServletRequest request, @RequestBody JSONObject json) {
        String userName = json.getString("userName");
        String password = json.getString("password");
        String captcha = json.getString("captcha");
        Assert.hasText(captcha, "验证码不能为空!");
        boolean valid = captchaService.validateResponseForID(request.getSession().getId(), captcha);
        Assert.isTrue(valid, "验证码不正确!");

        User user = userService.login(userName, password);
        json = new JSONObject();
        json.put("userName", user.getUserName());
        json.put("isSuperAdmin", user.isSuperAdmin());
        json.put("face", user.getUrl());

        UserRightContainer.loadUserRight(user);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 用户退出
     *
     * @author andy_hulibo@163.com
     * @date 2019/6/25 16:34
     */
    @GetMapping(value = "/logout")
    @Login(required = false)
    @ActionModel(value = "用户登出", recordLog = false)
    public void logout(HttpServletResponse response) {
        User user = getUser();
        if (user != null) {
            String userName = user.getUserName();
            userService.logout(userName);

            UserRightContainer.clear(user);
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 管理员列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/8 21:13
     */
    @GetMapping("/list")
    @ActionModel(value = "系统用户列表", recordLog = false)
    public void listUser(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);
        Map<String, Object> property = new HashMap<String, Object>(1) {
            private static final long serialVersionUID = 7166011434886278754L;

            {
                String roleId = request.getParameter("roleId");
                if (roleId != null) {
                    Set<UserRole> userRoleSet = new HashSet<>();
                    UserRole role = new UserRole();
                    role.setId(Integer.valueOf(roleId));
                    userRoleSet.add(role);
                    put("roles", userRoleSet);
                }
                String status = request.getParameter("status");
                if (status != null) {
                    put("status", status);
                }
            }
        };
        Pager pager = userService.getPage(property);
        JSONObject json = JsonUtil.toJsonFilter(pager, "password", "roles");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 新增管理员
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 11:53
     */
    @PostMapping(value = "/add")
    @ActionModel(value = "新增系统用户")
    public void addUser(@RequestBody @Valid User user, BindingResult result, HttpServletResponse response) {
        validData(result);
        userService.save(user);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 修改管理员
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:45
     */
    @PostMapping(value = "/edit")
    @ActionModel(value = "修改系统用户")
    public void editUser(@RequestBody @Valid User user, BindingResult result, HttpServletResponse response) {
        validData(result);
        userService.edit(user);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 获取管理员基本信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:50
     */
    @GetMapping(value = "/{userId}")
    @ActionModel(value = "查看系统用户详情", recordLog = false)
    public void viewUser(@PathVariable("userId") int userId, HttpServletResponse response) {
        User user = userService.get(userId);
        JSONObject json = JsonUtil.toJsonFilter(user, "password", "users", "menus");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 删除管理员信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:53
     */
    @PostMapping(value = "/del")
    @ActionModel(value = "删除系统用户")
    public void delUser(HttpServletResponse response, @RequestBody Integer... userId) {
        userService.remove(userId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 回收用户角色
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/12 13:51
     */
    @GetMapping(value = "/recycle/{userId}-{roleId}")
    @ActionModel(value = "回收用户角色")
    public void recycleUserRole(@PathVariable("userId") int userId, @PathVariable("roleId") int roleId, HttpServletResponse response) {
        userService.recyclingRole(userId, roleId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 修改当前登录用户的密码
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/29 14:52
     */
    @PostMapping("/edit/pwd")
    @ActionModel("修改密码")
    public void editPwd(HttpServletResponse response, @RequestBody Map<String, String> params) {
        userService.editPwd(getUser().getId(), params.get("oldPwd"), params.get("newPwd"));
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 重置密码
     *
     * @author andy_hulibo@163.com
     * @date 2020/7/1 11:35
     */
    @GetMapping(value = "/reset/pwd")
    @ActionModel("重置用户密码")
    public void resetPwd(HttpServletResponse response, @RequestParam("userName") String userName, @RequestParam("password") String password) {
        userService.resetPwd(userName, password);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 获得当前用户的菜单
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/30 9:48
     */
    @GetMapping(value = "/menus")
    @ActionModel(value = "获得当前用户的菜单", recordLog = false)
    public void getCurrentUserMenu(HttpServletResponse response) {
        List<SysMenu> menus = UserRightContainer.getCurrentUserSysMenus();
        JSONArray array = JsonUtil.toJsonFilterForArray(menus, "id", "seq", "parentId", "name", "url", "action");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(array));
    }
}
