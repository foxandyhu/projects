package com.bfly.cms.service;

import com.bfly.cms.entity.SysLog;
import com.bfly.core.base.service.IBaseService;
import com.bfly.cms.enums.LogsType;

/**
 * 系统日志操作业务接口
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/10 16:33
 */
public interface ISysLogService extends IBaseService<SysLog, Integer> {

    /**
     * 保存日志信息
     * @param title 标题
     * @param content 结果或参数
     * @param userName 操作者
     * @param category 操作类型
     * @param ip 操作IP
     * @param isMember 是否会员日志
     * @param success 是否成功
     * @param url 操作地址
     * @author andy_hulibo@163.com
     * @date 2019/6/27 17:24
     */
    void save(LogsType category, String userName, String ip, String url, String title, String content, boolean success,boolean isMember);
}
