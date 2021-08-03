package com.bfly.manage.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.entity.MemberGroup;
import com.bfly.cms.service.IMemberGroupService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.StringUtil;
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
import java.util.List;
import java.util.Map;

/**
 * 会员组Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/10 16:26
 */
@RestController
@RequestMapping(value = "/manage/member/group")
public class MemberGroupController extends BaseController {

    @Autowired
    private IMemberGroupService groupService;

    /**
     * 会员组列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 16:53
     */
    @GetMapping("/list")
    @ActionModel(value = "会员组列表", recordLog = false)
    public void listMemberGroup(HttpServletResponse response) {
        PagerThreadLocal.set(getRequest());
        Map<String, Sort.Direction> sortMap = new HashMap<>(1);
        sortMap.put("seq", Sort.Direction.ASC);
        Pager pager = groupService.getPage(null, null, sortMap);
        JSONObject json = JsonUtil.toJsonFilter(pager);
        JSONArray array = json.getJSONArray("data");
        if (array != null) {
            array.forEach(item -> {
                JSONObject jsonObject = (JSONObject) item;
                String allowUploadPerDay = "allowUploadPerDay";
                if (jsonObject.containsKey(allowUploadPerDay)) {
                    jsonObject.put("allowUploadPerDayHuman", StringUtil.getHumanSize(jsonObject.getInteger(allowUploadPerDay)*1024));
                }
                String allowUploadMaxFile = "allowUploadMaxFile";
                if (jsonObject.containsKey(allowUploadMaxFile)) {
                    jsonObject.put("allowUploadMaxFileHuman", StringUtil.getHumanSize(jsonObject.getInteger(allowUploadMaxFile)*1024));
                }
            });
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 得到所有的会员组集合
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/19 12:07
     */
    @GetMapping(value = "/all")
    @ActionModel(value = "所有会员组集合", recordLog = false)
    public void getAllMemberGroup(HttpServletResponse response) {
        Map<String, Sort.Direction> sortMap = new HashMap<>(1);
        sortMap.put("seq", Sort.Direction.ASC);
        List<MemberGroup> list = groupService.getList(null, null, sortMap);

        JSONArray array = new JSONArray();
        if (list != null) {
            for (MemberGroup group : list) {
                JSONObject json = new JSONObject();
                json.put("id", group.getId());
                json.put("name", group.getName());
                array.add(json);
            }
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(array));
    }

    /**
     * 新增会员组
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 15:00
     */
    @PostMapping(value = "/add")
    @ActionModel("新增会员组")
    public void addMemberGroup(@RequestBody @Valid MemberGroup group, BindingResult result, HttpServletResponse response) {
        validData(result);
        groupService.save(group);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 修改会员组
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:45
     */
    @PostMapping(value = "/edit")
    @ActionModel("修改会员组")
    public void editMemberGroup(@RequestBody @Valid MemberGroup group, BindingResult result, HttpServletResponse response) {
        groupService.edit(group);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 获取会员组基本信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:50
     */
    @GetMapping(value = "/{groupId}")
    @ActionModel(value = "会员组详情", recordLog = false)
    public void viewMemberGroup(@PathVariable("groupId") int groupId, HttpServletResponse response) {
        MemberGroup group = groupService.get(groupId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(group));
    }

    /**
     * 删除会员组
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 16:54
     */
    @PostMapping("/del")
    @ActionModel("删除会员组")
    public void removeMemberGroup(HttpServletResponse response, @RequestBody Integer... ids) {
        groupService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}
