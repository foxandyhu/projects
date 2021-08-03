package com.bfly.manage.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.entity.ScoreGroup;
import com.bfly.cms.service.IScoreGroupService;
import com.bfly.common.ResponseData;
import com.bfly.common.json.JsonUtil;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseController;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * 评分组Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/17 11:59
 */
@RestController
@RequestMapping(value = "/manage/score/group")
public class ScoreGroupController extends BaseController {

    @Autowired
    private IScoreGroupService scoreGroupService;

    /**
     * 评分组列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 12:00
     */
    @GetMapping(value = "/list")
    @ActionModel(value = "评分组列表", recordLog = false)
    public void listScoreGroup(HttpServletResponse response) {
        PagerThreadLocal.set(getRequest());
        Pager pager = scoreGroupService.getPage(null);
        JSONObject json = JsonUtil.toJsonFilter(pager, "items");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 获得所有评分组集合
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/14 18:54
     */
    @GetMapping(value = "/all")
    @ActionModel(value = "所有评分组", recordLog = false)
    public void getAllScoreGroup(HttpServletResponse response) {
        List<ScoreGroup> list = scoreGroupService.getList();
        JSONArray array = JsonUtil.toJsonFilterForArray(list, "items");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(array));
    }

    /**
     * 添加评分组
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 13:47
     */
    @PostMapping(value = "/add")
    @ActionModel(value = "新增评分组")
    public void addScoreGroup(@RequestBody @Valid ScoreGroup scoreGroup, BindingResult result, HttpServletResponse response) {
        validData(result);
        scoreGroupService.save(scoreGroup);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 修改评分组
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 13:48
     */
    @PostMapping(value = "/edit")
    @ActionModel(value = "修改评分组")
    public void editScoreGroup(@RequestBody @Valid ScoreGroup scoreGroup, BindingResult result, HttpServletResponse response) {
        validData(result);
        scoreGroupService.edit(scoreGroup);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 评分组详情信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 13:50
     */
    @GetMapping(value = "/{scoreGroupId}")
    @ActionModel(value = "评分组详情", recordLog = false)
    public void viewScoreGroup(@PathVariable("scoreGroupId") int scoreGroupId, HttpServletResponse response) {
        ScoreGroup scoreGroup = scoreGroupService.get(scoreGroupId);
        JSONObject json = JsonUtil.toJsonFilter(scoreGroup, "items");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 删除评分组
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 13:51
     */
    @PostMapping(value = "/del")
    public void delScoreGroup(HttpServletResponse response, @RequestBody Integer... ids) {
        scoreGroupService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}
