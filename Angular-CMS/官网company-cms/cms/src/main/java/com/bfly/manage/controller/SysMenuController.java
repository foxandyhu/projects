package com.bfly.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.entity.SysMenu;
import com.bfly.cms.service.ISysMenuService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.json.JsonUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseController;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统菜单Controller
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/12 18:39
 */
@RestController
@RequestMapping(value = "/manage/menu")
public class SysMenuController extends BaseController {

    @Autowired
    private ISysMenuService menuService;

    /**
     * 菜单列表
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/12 18:40
     */
    @GetMapping(value = "/list")
    @ActionModel(value = "系统菜单列表", recordLog = false)
    public void listSysMenu(HttpServletResponse response) {
        PagerThreadLocal.set(getRequest(), 100);

        Map<String, Object> queryMap = new HashMap<>(1);
        queryMap.put("parent", 0);

        Map<String, Sort.Direction> sortMap = new HashMap<>(1);
        sortMap.put("seq", Sort.Direction.ASC);

        Pager pager = menuService.getPage(queryMap, null, sortMap);
        JSONObject json = JsonUtil.toJsonFilter(pager, "parent", "roles");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 添加系统菜单
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/12 18:40
     */
    @PostMapping(value = "/add")
    @ActionModel(value = "添加系统菜单")
    public void addSysMenu(@RequestBody @Valid SysMenu menu, BindingResult result, HttpServletResponse response) {
        validData(result);
        menuService.save(menu);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 修改系统菜单
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/12 18:41
     */
    @PostMapping(value = "/edit")
    @ActionModel(value = "修改系统菜单")
    public void editSysMenu(@RequestBody @Valid SysMenu menu, BindingResult result, HttpServletResponse response) {
        validData(result);
        menuService.edit(menu);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 删除系统菜单
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/12 18:41
     */
    @PostMapping(value = "/del")
    @ActionModel(value = "删除系统菜单")
    public void delSysMenu(HttpServletResponse response, @RequestBody Integer... menuId) {
        menuService.remove(menuId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 系统菜单详情
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/29 21:25
     */
    @GetMapping(value = "/{menuId}")
    @ActionModel(value = "系统菜单详情", recordLog = false)
    public void detailSysMenu(HttpServletResponse response, @PathVariable("menuId") int menuId) {
        SysMenu menu = menuService.get(menuId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(menu));
    }
}
