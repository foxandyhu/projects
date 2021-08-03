package com.bfly.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.entity.LoginConfig;
import com.bfly.cms.entity.Member;
import com.bfly.cms.entity.RegistConfig;
import com.bfly.cms.enums.MemberStatus;
import com.bfly.cms.enums.SysError;
import com.bfly.cms.service.IMemberLoginConfigService;
import com.bfly.cms.service.IMemberRegistConfigService;
import com.bfly.cms.service.IMemberService;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.json.JsonUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseController;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.exception.WsResponseException;
import com.bfly.core.security.ActionModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 会员Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/10 14:57
 */
@RestController
@RequestMapping(value = "/manage/member")
public class MemberController extends BaseController {

    @Autowired
    private IMemberService memberService;
    @Autowired
    private IMemberLoginConfigService loginConfigService;
    @Autowired
    private IMemberRegistConfigService registConfigService;

    /**
     * 会员列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 14:59
     */
    @GetMapping("/list")
    @ActionModel(value = "会员列表", recordLog = false)
    public void listMember(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);
        Map<String, Object> exactMap = new HashMap<>(1);
        String statusStr = request.getParameter("status");
        if (StringUtils.isNotBlank(statusStr)) {
            exactMap.put("status", DataConvertUtils.convertToInteger(statusStr));
        }

        Map<String, String> unExactMap = new HashMap<>(3);
        String userName = request.getParameter("userName");
        if (StringUtils.isNotBlank(userName)) {
            unExactMap.put("userName", userName);
        }
        String email = request.getParameter("email");
        if (StringUtils.isNotBlank(email)) {
            unExactMap.put("email", email);
        }
        Pager pager = memberService.getPage(exactMap, unExactMap, null);
        JSONObject json = JsonUtil.toJsonFilter(pager, "memberExt");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 新增会员
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 15:00
     */
    @PostMapping(value = "/add")
    @ActionModel("新增会员")
    public void addMember(@RequestBody @Valid Member member, BindingResult result, HttpServletResponse response) {
        validData(result);
        memberService.save(member);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 修改会员
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:45
     */
    @PostMapping(value = "/edit")
    @ActionModel("修改会员信息")
    public void editMember(@RequestBody @Valid Member member, BindingResult result, HttpServletResponse response) {
        validData(result);
        memberService.edit(member);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 获取会员基本信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:50
     */
    @GetMapping(value = "/{memberId}")
    @ActionModel(value = "会员详情", recordLog = false)
    public void viewMember(@PathVariable("memberId") int memberId, HttpServletResponse response) {
        Member member = memberService.get(memberId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(member));
    }

    /**
     * 删除会员信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 13:53
     */
    @PostMapping(value = "/del")
    @ActionModel("删除会员")
    public void removeMember(HttpServletResponse response, @RequestBody Integer... ids) {
        memberService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 用户名检查
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 15:40
     */
    @PostMapping(value = "/check")
    @ActionModel(value = "用户名重复检查", recordLog = false)
    public void checkUserName(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        long count = memberService.getCount(new HashMap<String, Object>(1) {
            private static final long serialVersionUID = 4990379673489715199L;

            {
                put("userName", username);
            }
        });
        if (count > 0) {
            throw new WsResponseException(SysError.DATA_REPEAT, "用户名已存在!");
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 修改状态
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/20 9:37
     */
    @GetMapping(value = "/edit/{memberId}-{status}")
    @ActionModel("修改会员账户状态")
    public void editMemberStatus(HttpServletResponse response, @PathVariable("memberId") int memberId, @PathVariable("status") int status) {
        memberService.editMemberStatus(memberId, MemberStatus.getStatus(status));
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 修改会员账户密码
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/20 10:21
     */
    @PostMapping(value = "/editpwd")
    @ActionModel("修改会员账户密码")
    public void editMemberPassword(HttpServletResponse response, @RequestBody Map<String, String> params) {
        int memberId = DataConvertUtils.convertToInteger(params.get("memberId"));
        String password = params.get("password");
        memberService.editMemberPassword(memberId, password);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 获得会员配置信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/20 10:44
     */
    @GetMapping(value = "/config/login")
    @ActionModel(value = "会员登录配置详情", recordLog = false)
    public void getMemberLoginConfig(HttpServletResponse response) {
        LoginConfig config = loginConfigService.getLoginConfig();
        JSONObject json = JsonUtil.toJsonFilter(config, "userName", "password", "host");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 获得会员注册配置信息
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/14 22:58
     */
    @GetMapping(value = "/config/register")
    @ActionModel(value = "会员注册配置详情", recordLog = false)
    public void getMemberRegisterConfig(HttpServletResponse response) {
        RegistConfig config = registConfigService.getRegistConfig();
        JSONObject json = JsonUtil.toJsonFilter(config, "userName", "password", "host");
        ResponseUtil.writeJson(response, ResponseData.getSuccess(json));
    }

    /**
     * 修改会员登录配置信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/20 10:46
     */
    @PostMapping(value = "/config/login/edit")
    @ActionModel("修改会员登录配置信息")
    public void editMemberLoginConfig(@RequestBody LoginConfig config, BindingResult result, HttpServletResponse response) {
        validData(result);
        loginConfigService.editMemberLoginConfig(config);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }


    /**
     * 修改会员注册配置信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/20 10:46
     */
    @PostMapping(value = "/config/regist/edit")
    @ActionModel("修改会员注册配置信息")
    public void editMemberRegisterConfig(@RequestBody RegistConfig config, BindingResult result, HttpServletResponse response) {
        validData(result);
        registConfigService.editMemberRegisterConfig(config);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}
