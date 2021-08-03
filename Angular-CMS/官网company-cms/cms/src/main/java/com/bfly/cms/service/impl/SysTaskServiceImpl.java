package com.bfly.cms.service.impl;

import com.bfly.cms.dao.ISysTaskDao;
import com.bfly.cms.entity.SysTask;
import com.bfly.cms.service.ISysTaskService;
import com.bfly.common.DateUtil;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.cms.enums.TaskStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/14 11:57
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class SysTaskServiceImpl extends BaseServiceImpl<SysTask, Integer> implements ISysTaskService {

    @Autowired
    private ISysTaskDao taskDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean startTask(String name) {
        SysTask task = taskDao.getTaskByName(name);
        task.setStatus(TaskStatus.START.getId());
        Date nextDate = DateUtil.getNextDateByCron(task.getPeriod());
        task.setNextExecTime(nextDate);
        return edit(task);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean stopTask(String name) {
        SysTask task = taskDao.getTaskByName(name);
        task.setStatus(TaskStatus.shutdown.getId());
        return edit(task);
    }

    @Override
    public SysTask getTask(String name) {
        return taskDao.getTaskByName(name);
    }
}
