package com.bfly.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.entity.ScoreGroup;
import com.bfly.cms.entity.ScoreItem;
import com.bfly.cms.service.IScoreItemService;
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
 * 评分组Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/17 11:59
 */
@RestController
@RequestMapping(value = "/manage/score/item")
public class ScoreItemController extends BaseController {

    @Autowired
    private IScoreItemService scoreItemService;

    /**
     * 根据评分组ID查看评分项列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 12:00
     */
    @GetMapping(value = "/list")
    @ActionModel(value = "评分项列表", recordLog = false)
    public void listScoreItem(HttpServletResponse response) {
        PagerThreadLocal.set(getRequest());
        String groupIdStr = getRequest().getParameter("groupId");
        Map<String, Object> params = null;
        if (groupIdStr != null) {
            params = new HashMap<>(1);
            ScoreGroup group = new ScoreGroup();
            group.setId(Integer.valueOf(groupIdStr));
            params.put("group", group);
        }

        Map<String, Sort.Direction> sortParam = new HashMap<>(1);
        sortParam.put("seq", Sort.Direction.ASC);
        Pager pager = scoreItemService.getPage(params, null, sortParam);
        JSONObject json = JsonUtil.toJsonFilter(pager, "items");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 添加评分项
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 13:47
     */
    @PostMapping(value = "/add")
    @ActionModel("新增评分项")
    public void addScoreItem(@RequestBody @Valid ScoreItem scoreItem, BindingResult result, HttpServletResponse response) {
        validData(result);
        scoreItemService.save(scoreItem);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 修改评分项
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 13:48
     */
    @PostMapping(value = "/edit")
    @ActionModel("修改评分项")
    public void editScoreItem(@RequestBody @Valid ScoreItem scoreItem, BindingResult result, HttpServletResponse response) {
        validData(result);
        scoreItemService.edit(scoreItem);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 评分项详情信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 13:50
     */
    @GetMapping(value = "/{scoreItemId}")
    @ActionModel(value = "查看评分项详情", recordLog = false)
    public void viewScoreItem(@PathVariable("scoreItemId") int scoreItemId, HttpServletResponse response) {
        ScoreItem scoreItem = scoreItemService.get(scoreItemId);
        JSONObject json = JsonUtil.toJsonFilter(scoreItem, "items");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 删除评分项
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/17 13:51
     */
    @PostMapping(value = "/del")
    @ActionModel(value = "删除评分项")
    public void delScoreItem(HttpServletResponse response, @RequestBody Integer... ids) {
        scoreItemService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 评分项排序
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/4 14:54
     */
    @GetMapping(value = "/sort/{upItemId}-{downItemId}")
    @ActionModel("评分项排序")
    public void sortScoreIte(HttpServletResponse response, @PathVariable("upItemId") int upItemId, @PathVariable("downItemId") int downItemId) {
        scoreItemService.sortScoreItem(upItemId, downItemId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}
