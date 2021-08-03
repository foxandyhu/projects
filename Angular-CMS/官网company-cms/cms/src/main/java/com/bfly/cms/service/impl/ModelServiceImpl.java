package com.bfly.cms.service.impl;

import com.bfly.cms.dao.IModelDao;
import com.bfly.cms.entity.Model;
import com.bfly.cms.service.IModelService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/19 10:31
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class ModelServiceImpl extends BaseServiceImpl<Model, Integer> implements IModelService {

    @Autowired
    private IModelDao modelDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(Model model) {
        Model m = get(model.getId());
        Assert.notNull(m, "不存在的模型信息!");
        model.setSeq(m.getSeq());
        return super.edit(model);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Model model) {
        int maxSeq = modelDao.getMaxSeq();
        model.setSeq(++maxSeq);
        return super.save(model);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void sortModel(int upItemId, int downItemId) {
        Model upItem = get(upItemId);
        Assert.notNull(upItem, "不存在的模型!");

        Model downItem = get(downItemId);
        Assert.notNull(downItem, "不存在的模型!");

        int downSeq = downItem.getSeq();

        modelDao.editModelSeq(downItemId, upItem.getSeq());
        modelDao.editModelSeq(upItemId, downSeq);

        getCache().clear();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean editModelEnabled(int modelId, boolean enabled) {
        boolean flag = modelDao.editModelEnabled(modelId, enabled) > 0;
        if (flag) {
            getCache().clear();
        }
        return flag;
    }
}
