package com.bfly.cms.service;

import com.bfly.cms.entity.CommentConfig;
import com.bfly.core.base.service.IBaseService;

/**
 * @author andy_hulibo@163.com
 * @date 2019/9/15 13:28
 */
public interface ICommentConfigService extends IBaseService<CommentConfig, Integer> {

    /**
     * 得到评论配置
     * @return
     * @author andy_hulibo@163.com
     * @date 2019/9/15 13:28
     */
    CommentConfig getConfig();
}
