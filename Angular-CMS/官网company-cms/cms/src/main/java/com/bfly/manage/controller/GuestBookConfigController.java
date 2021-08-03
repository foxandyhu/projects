package com.bfly.manage.controller;

import com.bfly.cms.entity.GuestBookConfig;
import com.bfly.cms.service.IGuestBookConfigService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.core.base.action.BaseController;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 留言Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/12 14:14
 */
@RestController
@RequestMapping(value = "/manage/guestbook")
public class GuestBookConfigController extends BaseController {

    @Autowired
    private IGuestBookConfigService configService;

    /**
     * 得到留言配置
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/15 13:38
     */
    @GetMapping(value = "/config")
    @ActionModel(value = "留言配置", recordLog = false)
    public void getConfig(HttpServletResponse response) {
        GuestBookConfig config = configService.getConfig();
        ResponseUtil.writeJson(response, ResponseData.getSuccess(config));
    }

    /**
     * 编辑配置
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/15 13:39
     */
    @PostMapping(value = "/config/edit")
    @ActionModel(value = "编辑留言配置")
    public void editConfig(HttpServletResponse response, @RequestBody GuestBookConfig config) {
        configService.save(config);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}
