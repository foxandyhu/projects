package com.bfly.cms.service.impl;

import com.bfly.cms.dao.IAdDao;
import com.bfly.cms.dao.IAdTxtDao;
import com.bfly.cms.entity.Ad;
import com.bfly.cms.entity.AdTxt;
import com.bfly.cms.enums.AdStatusEnum;
import com.bfly.cms.enums.AdTypeEnum;
import com.bfly.cms.enums.SysError;
import com.bfly.cms.service.IAdService;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.exception.WsResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/11 17:09
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class AdServiceImpl extends BaseServiceImpl<Ad, Integer> implements IAdService {

    @Autowired
    private IAdTxtDao txtDao;
    @Autowired
    private IAdDao adDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Ad ad) {
        checkAd(ad);
        AdTxt txt = ad.getTxt();

        ad.setTxt(null);
        boolean flag = super.save(ad);
        if (flag) {
            txt.setAdId(ad.getId());
            txtDao.save(txt);
        }

        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(Ad adInfo) {
        checkAd(adInfo);
        Ad dbAd = get(adInfo.getId());
        if (dbAd == null) {
            throw new WsResponseException(SysError.MISSING_PARAM, "广告信息不存在!");
        }
        AdTxt txt = adInfo.getTxt();
        adInfo.setTxt(null);

        boolean flag = super.edit(adInfo);
        if (flag) {
            txt.setAdId(dbAd.getId());
            txtDao.save(txt);
        }
        return flag;
    }


    /**
     * 对广告信息校验
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/18 10:42
     */
    private void checkAd(Ad ad) {
        Assert.isTrue(ad.getStartTime() != null, "开始时间不能为空!");
        Assert.isTrue(ad.getEndTime() != null, "结束时间不能为空!");
        AdTypeEnum type = AdTypeEnum.getType(ad.getType());

        if (ad.getStartTime().after(ad.getEndTime())) {
            throw new WsResponseException(SysError.PARAM_ERROR, "开始时间需小于结束时间!");
        }

        Date now = new Date();
        if (ad.getStartTime().before(now) && ad.getEndTime().after(now)) {
            ad.setStatus(AdStatusEnum.PUBLISHING.getId());
        } else if (ad.getStartTime().after(now)) {
            ad.setStatus(AdStatusEnum.UNPUBLISHED.getId());
        } else if (ad.getEndTime().before(now)) {
            ad.setStatus(AdStatusEnum.PUBLISH_EXPIRED.getId());
        }

        try {
            switch (type) {
                case PIC:
                    Assert.hasLength(ad.getTxt().getPicPath(), "图片地址不能为空!");
                    break;
                case TEXT:
                    Assert.hasLength(ad.getTxt().getTxtContent(), "广告文字不能为空!");
                    break;
                case CODE:
                    Assert.hasLength(ad.getTxt().getCodeContent(), "代码内容不能为空!");
                    break;
                default:
                    throw new RuntimeException("广告类型错误!");
            }
        } catch (RuntimeException e) {
            throw new WsResponseException(SysError.MISSING_PARAM, e.getMessage());
        }
    }

    @Override
    @Transactional
    public void disableExpiredAds() {
        adDao.disabledExpiredAds(new Date(), AdStatusEnum.PUBLISH_EXPIRED.getId());
    }

    @Override
    @Transactional
    public void enabledUnExpireAds() {
        adDao.enabledUnExpireAds(new Date(), AdStatusEnum.PUBLISHING.getId());
    }
}
