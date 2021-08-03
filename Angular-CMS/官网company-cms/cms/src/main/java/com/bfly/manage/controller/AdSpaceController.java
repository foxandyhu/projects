package com.bfly.manage.controller;

import com.alibaba.fastjson.JSONArray;
import com.bfly.cms.entity.AdSpace;
import com.bfly.cms.service.IAdSpaceService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.json.JsonUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseController;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 广告位管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/11 16:53
 */
@RestController
@RequestMapping(value = "/manage/ad/space")
public class AdSpaceController extends BaseController {

    @Autowired
    private IAdSpaceService adSpaceService;

    /**
     * 广告位列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 16:53
     */
    @GetMapping("/list")
    @ActionModel(value = "广告位列表", recordLog = false)
    public void listAdSpace(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);
        Pager pager = adSpaceService.getPage(null);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(pager));
    }

    /**
     * 获得所有的广告位集合
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/18 12:49
     */
    @GetMapping(value = "/all")
    @ActionModel(value = "获得所有的广告位集合", recordLog = false)
    public void getAllSpace(HttpServletResponse response) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("enabled", true);
        List<AdSpace> list = adSpaceService.getList(params);
        JSONArray array = JsonUtil.toJsonFilterForArray(list, "remark", "enabled");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(array));
    }

    /**
     * 新增广告位
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 16:57
     */
    @PostMapping(value = "/add")
    @ActionModel("新增广告位")
    public void addAdSpace(@RequestBody @Valid AdSpace adSpace, BindingResult result, HttpServletResponse response) {
        validData(result);
        adSpaceService.save(adSpace);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 修改广告位
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:01
     */
    @PostMapping(value = "/edit")
    @ActionModel("修改广告位")
    public void editAdSpace(@RequestBody @Valid AdSpace adSpace, BindingResult result, HttpServletResponse response) {
        validData(result);
        adSpaceService.edit(adSpace);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 获取广告位基本信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:02
     */
    @GetMapping(value = "/{spaceId}")
    @ActionModel(value = "广告位详情", recordLog = false)
    public void viewAdSpace(@PathVariable("spaceId") int spaceId, HttpServletResponse response) {
        AdSpace adSpace = adSpaceService.get(spaceId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(adSpace));
    }

    /**
     * 删除广告位
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:02
     */
    @PostMapping(value = "/del")
    @ActionModel("删除广告位")
    public void removeAdSpace(HttpServletResponse response, @RequestBody Integer... ids) {
        adSpaceService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}
