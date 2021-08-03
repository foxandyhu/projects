package com.bfly.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.entity.SpecialTopic;
import com.bfly.cms.service.ISpecialTopicService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.json.JsonUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseController;
import com.bfly.core.config.ResourceConfigure;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 专题Controller
 *
 * @author andy_hulibo@163.com
 * @date 2019/8/6 15:34
 */
@RestController
@RequestMapping(value = "/manage/topic")
public class SpecialTopicController extends BaseController {

    @Autowired
    private ISpecialTopicService topicService;

    /**
     * 专题列表
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/6 15:35
     */
    @GetMapping(value = "/list")
    @ActionModel(value = "专题列表", recordLog = false)
    public void listSpecialTopic(HttpServletResponse response) {
        PagerThreadLocal.set(getRequest());

        Map<String, Sort.Direction> sortMap = new HashMap<>(1);
        sortMap.put("seq", Sort.Direction.ASC);
        Pager pager = topicService.getPage(null, null, sortMap);
        JSONObject json = JsonUtil.toJsonFilter(pager);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 添加专题
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/6 15:39
     */
    @PostMapping(value = "/add")
    @ActionModel("添加专题")
    public void addSpecialTopic(@RequestBody @Valid SpecialTopic topic, BindingResult result, HttpServletResponse response) {
        validData(result);
        topicService.save(topic);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }


    /**
     * 修改专题
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/6 15:39
     */
    @PostMapping(value = "/edit")
    @ActionModel("编辑专题")
    public void editSpecialTopic(@RequestBody @Valid SpecialTopic topic, BindingResult result, HttpServletResponse response) {
        validData(result);
        topicService.edit(topic);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 查看专题信息
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/6 15:40
     */
    @GetMapping(value = "/{topicId}")
    @ActionModel(value = "栏目详情", recordLog = false)
    public void viewSpecialTopic(@PathVariable("topicId") int topicId, HttpServletResponse response) {
        SpecialTopic topic = topicService.get(topicId);
        JSONObject json = JsonUtil.toJsonFilter(topic);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 删除专题
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/6 15:41
     */
    @PostMapping(value = "/del")
    @ActionModel("删除专题")
    public void delSpecialTopic(HttpServletResponse response, @RequestBody Integer... ids) {
        topicService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 专题排序
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/6 15:41
     */
    @GetMapping(value = "/sort/{upItemId}-{downItemId}")
    @ActionModel("专题排序")
    public void sortSpecialTopic(HttpServletResponse response, @PathVariable("upItemId") int upItemId, @PathVariable("downItemId") int downItemId) {
        topicService.sortSpecialTopic(upItemId, downItemId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 获得文章关联的专题
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/8 20:04
     */
    @GetMapping(value = "/article/{articleId}")
    @ActionModel(value = "获得文章关联的专题", recordLog = false)
    public void getSpecialTopicForArticle(HttpServletResponse response, @PathVariable("articleId") int articleId) {
        List<SpecialTopic> topics = topicService.getSpecialTopicForArticle(articleId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(topics));
    }
}
