package com.bfly.manage.controller;

import com.bfly.cms.entity.CommentConfig;
import com.bfly.cms.service.ICommentConfigService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.core.base.action.BaseController;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * 内容评论Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/12 14:14
 */
@RestController
@RequestMapping(value = "/manage/comment")
public class CommentConfigController extends BaseController {

    @Autowired
    private ICommentConfigService configService;

    /**
     * 得到评论配置
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/15 13:38
     */
    @GetMapping(value = "/config")
    @ActionModel(value = "评论配置", recordLog = false)
    public void getConfig(HttpServletResponse response) {
        CommentConfig config = configService.getConfig();
        ResponseUtil.writeJson(response, ResponseData.getSuccess(config));
    }

    /**
     * 编辑配置
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/15 13:39
     */
    @PostMapping(value = "/config/edit")
    @ActionModel(value = "编辑评论配置")
    public void editConfig(HttpServletResponse response, @RequestBody CommentConfig config) {
        configService.save(config);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}
