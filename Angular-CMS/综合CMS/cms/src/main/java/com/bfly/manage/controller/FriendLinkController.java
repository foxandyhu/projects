package com.bfly.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.entity.FriendLink;
import com.bfly.cms.service.IFriendLinkService;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.json.JsonUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseController;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.ActionModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 友情链接Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/11 16:53
 */
@RestController
@RequestMapping(value = "/manage/friendlink")
public class FriendLinkController extends BaseController {

    @Autowired
    private IFriendLinkService friendLinkService;

    /**
     * 友情链接列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 16:53
     */
    @GetMapping("/list")
    @ActionModel(value = "友情链接列表", recordLog = false)
    public void listFriendLink(HttpServletResponse response) {
        PagerThreadLocal.set(getRequest());

        Map<String, Object> params = null;
        String type = getRequest().getParameter("type");
        if (StringUtils.isNotBlank(type)) {
            params = new HashMap<>(1);
            params.put("type", DataConvertUtils.convertToInteger(type));
        }
        Map<String, Sort.Direction> sortMap = new HashMap<>();
        sortMap.put("seq", Sort.Direction.DESC);

        Pager pager = friendLinkService.getPage(params, null, sortMap);
        JSONObject json = JsonUtil.toJsonFilter(pager);

        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 新增友情链接
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 16:57
     */
    @PostMapping(value = "/add")
    @ActionModel(value = "新增友情链接")
    public void addFriendLink(@RequestBody @Valid FriendLink friendLink, BindingResult result, HttpServletResponse response) {
        validData(result);
        friendLinkService.save(friendLink);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 修改友情链接
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:01
     */
    @PostMapping(value = "/edit")
    @ActionModel(value = "修改友情链接")
    public void editFriendLink(@RequestBody @Valid FriendLink friendLink, BindingResult result, HttpServletResponse response) {
        validData(result);
        friendLinkService.edit(friendLink);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 获取友情链接基本信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:02
     */
    @GetMapping(value = "/{linkId}")
    @ActionModel(value = "友情链接详情", recordLog = false)
    public void viewFriendLink(@PathVariable("linkId") int linkId, HttpServletResponse response) {
        FriendLink friendLink = friendLinkService.get(linkId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(friendLink));
    }

    /**
     * 删除友情链接
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:02
     */
    @PostMapping(value = "/del")
    @ActionModel(value = "删除友情链接")
    public void removeFriendLink(HttpServletResponse response, @RequestBody Integer... ids) {
        friendLinkService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 友情链接排序
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/4 14:54
     */
    @GetMapping(value = "/sort/{upItemId}-{downItemId}")
    @ActionModel("友情链接排序")
    public void sortFriendLink(HttpServletResponse response, @PathVariable("upItemId") int upItemId, @PathVariable("downItemId") int downItemId) {
        friendLinkService.sortFriendLink(upItemId, downItemId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}
