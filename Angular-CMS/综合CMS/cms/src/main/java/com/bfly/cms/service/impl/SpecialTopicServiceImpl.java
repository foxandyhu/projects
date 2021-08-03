package com.bfly.cms.service.impl;

import com.bfly.cms.dao.ISpecialTopicDao;
import com.bfly.cms.entity.SpecialTopic;
import com.bfly.cms.service.ISpecialTopicService;
import com.bfly.cms.service.ISysWaterMarkService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.config.ResourceConfigure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.File;
import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2019/8/4 15:10
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class SpecialTopicServiceImpl extends BaseServiceImpl<SpecialTopic, Integer> implements ISpecialTopicService {

    @Autowired
    private ISpecialTopicDao topicDao;
    @Autowired
    private ISysWaterMarkService waterMarkService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SpecialTopic topic) {
        int maxSeq = topicDao.getMaxSeq();
        topic.setSeq(++maxSeq);
        waterMarkService.waterMarkFile(topic.getTitleImg());

        waterMarkService.waterMarkFile(topic.getContentImg());

        String path = ResourceConfigure.getTemplateAbsolutePath("topic/pc");
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        path = ResourceConfigure.getTemplateAbsolutePath("topic/mobile");
        file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return super.save(topic);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(SpecialTopic topic) {
        SpecialTopic st = get(topic.getId());
        Assert.notNull(st, "不存在的专题信息!");

        waterMarkService.waterMarkFile(topic.getTitleImg());
        waterMarkService.waterMarkFile(topic.getContentImg());

        topic.setSeq(st.getSeq());
        return super.edit(topic);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sortSpecialTopic(int upId, int downId) {
        SpecialTopic upTopic = get(upId);
        Assert.notNull(upTopic, "不存在的专题信息!");

        SpecialTopic downTopic = get(downId);
        Assert.notNull(downTopic, "不存在的专题信息!");

        topicDao.editSpecialTopicSeq(upId, downTopic.getSeq());
        topicDao.editSpecialTopicSeq(downId, upTopic.getSeq());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int remove(Integer... ids) {
        for (Integer id : ids) {
            if (id != null) {
                topicDao.clearSpecialTopicArticleShip(id);
            }
        }
        return super.remove(ids);
    }

    @Override
    public List<SpecialTopic> getSpecialTopicForArticle(int articleId) {
        return topicDao.getSpecialTopicForArticle(articleId);
    }
}
