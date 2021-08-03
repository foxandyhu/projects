package com.bfly.manage.controller;

import com.bfly.cms.entity.Comment;
import com.bfly.cms.enums.CommentStatus;
import com.bfly.cms.service.ICommentService;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseController;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.ActionModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 内容评论Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/12 14:14
 */
@RestController
@RequestMapping(value = "/manage/comment")
public class CommentController extends BaseController {

    @Autowired
    private ICommentService commentService;

    /**
     * 评论列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:14
     */
    @GetMapping("/list")
    @ActionModel(value = "评论列表", recordLog = false)
    public void listComment(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);
        Map<String, Object> property = new HashMap(2);
        String status = request.getParameter("status");
        String recommend = request.getParameter("recommend");
        String articleId = request.getParameter("articleId");
        if (StringUtils.isNotBlank(status)) {
            property.put("status", DataConvertUtils.convertToInteger(status));
        }
        if (StringUtils.isNotBlank(recommend)) {
            property.put("recommend", DataConvertUtils.convertToBoolean(recommend));
        }
        if (StringUtils.isNotBlank(articleId)) {
            property.put("articleId", DataConvertUtils.convertToInteger(articleId));
        }
        Pager pager = commentService.getPage(property);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(pager));
    }

    /**
     * 管理员回复评论
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:16
     */
    @PostMapping(value = "/reply")
    @ActionModel(value = "回复评论")
    public void replyComment(@RequestBody Comment comment, HttpServletResponse response) {
        comment.setUserName(getUser().getUserName());
        commentService.save(comment);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 管理员审核评论
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:16
     */
    @PostMapping(value = "/verify/{status}")
    @ActionModel(value = "审核评论")
    public void verifyComment(HttpServletResponse response, @PathVariable("status") boolean status, @RequestBody Integer... ids) {
        commentService.verifyComment(status ? CommentStatus.PASSED : CommentStatus.UNPASSED, ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 推荐或取消评论
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:23
     */
    @ActionModel(value = "推荐/取消评论")
    @GetMapping(value = "/recommend/{commentId}-{recommend}")
    public void recommendComment(@PathVariable("commentId") int commentId, @PathVariable("recommend") boolean recommend, HttpServletResponse response) {
        commentService.recommendComment(commentId, recommend);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 删除评论
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/12 14:25
     */
    @PostMapping(value = "/del")
    @ActionModel(value = "删除评论")
    public void removeComment(HttpServletResponse response, @RequestBody Integer... ids) {
        commentService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}
