package com.bfly.cms.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * 文章内容评论
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 16:50
 */
@Entity
@Table(name = "comment_config")
public class CommentConfig implements Serializable {


    private static final long serialVersionUID = 1134606068826027508L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 是否开启评论
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/21 18:13
     */
    @Column(name = "is_open_comment")
    private boolean openComment;

    /**
     * 评论是否登录
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/21 18:15
     */
    @Column(name = "is_need_login_comment")
    private boolean needLoginComment;

    /**
     * 评论日最高限制数
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/21 18:16
     */
    @Column(name = "max_comment_limit")
    @Min(value = 0, message = "日评论数最小为0!")
    private int maxCommentLimit;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOpenComment() {
        return openComment;
    }

    public void setOpenComment(boolean openComment) {
        this.openComment = openComment;
    }

    public boolean isNeedLoginComment() {
        return needLoginComment;
    }

    public void setNeedLoginComment(boolean needLoginComment) {
        this.needLoginComment = needLoginComment;
    }

    public int getMaxCommentLimit() {
        return maxCommentLimit;
    }

    public void setMaxCommentLimit(int maxCommentLimit) {
        this.maxCommentLimit = maxCommentLimit;
    }
}