package com.bfly.cms.service.impl;

import com.bfly.cms.dao.IScoreItemDao;
import com.bfly.cms.entity.ScoreGroup;
import com.bfly.cms.entity.ScoreItem;
import com.bfly.cms.service.IScoreGroupService;
import com.bfly.cms.service.IScoreItemService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/17 11:54
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class ScoreItemServiceImpl extends BaseServiceImpl<ScoreItem, Integer> implements IScoreItemService {

    @Autowired
    private IScoreGroupService groupService;
    @Autowired
    private IScoreItemDao scoreItemDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(ScoreItem scoreItem) {
        Assert.notNull(scoreItem.getGroup(), "未选择评分组!");
        ScoreGroup group = groupService.get(scoreItem.getGroup().getId());
        Assert.notNull(group, "未选择评分组!");

        int maxSeq = scoreItemDao.getMaxSeq(group.getId());
        scoreItem.setSeq(++maxSeq);
        return super.save(scoreItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(ScoreItem scoreItem) {
        Assert.notNull(scoreItem.getGroup(), "未选择评分组!");
        ScoreGroup group = groupService.get(scoreItem.getGroup().getId());
        Assert.notNull(group, "未选择评分组!");

        ScoreItem item = get(scoreItem.getId());
        Assert.notNull(item, "不存在该评分项!");
        Assert.isTrue(item.getGroup().getId().equals(group.getId()), "评分项和评分组不对应!");

        scoreItem.setGroup(group);
        scoreItem.setSeq(item.getSeq());
        return super.edit(scoreItem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeScoreItem(int groupId) {
        return scoreItemDao.removeScoreItems(groupId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sortScoreItem(int upId, int downId) {
        ScoreItem upItem = get(upId);
        Assert.notNull(upItem, "不存在的栏目信息!");

        ScoreItem downItem = get(downId);
        Assert.notNull(downItem, "不存在的栏目信息!");

        scoreItemDao.editScoreItemSeq(upId, downItem.getSeq());
        scoreItemDao.editScoreItemSeq(downId, upItem.getSeq());
    }

    @Override
    public List<Map<String, Object>> getArticleScoreItems(int articleId, int scoreGroupId) {
        return scoreItemDao.getArticleScoreItems(articleId, scoreGroupId);
    }
}
