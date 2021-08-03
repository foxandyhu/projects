package com.bfly.cms.service.impl;

import com.bfly.cms.entity.SiteConfig;
import com.bfly.cms.service.ISiteConfigService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/18 11:03
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class SiteConfigServiceImpl extends BaseServiceImpl<SiteConfig, Integer> implements ISiteConfigService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(SiteConfig siteConfig) {
        SiteConfig site = getSite();
        //不存在则新增 存在则修改
        if (site == null) {
            return super.save(siteConfig);
        }
        siteConfig.setId(site.getId());
        return super.edit(siteConfig);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(SiteConfig siteConfig) {
        return edit(siteConfig);
    }

    @Override
    public SiteConfig getSite() {
        List<SiteConfig> sites = getList();
        return sites != null && !sites.isEmpty() ? sites.get(0) : null;
    }
}
