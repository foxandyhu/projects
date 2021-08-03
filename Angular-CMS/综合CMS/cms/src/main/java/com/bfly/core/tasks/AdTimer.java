package com.bfly.core.tasks;

import com.bfly.cms.service.IAdService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Calendar;

/**
 * 广告定时器检查失效的广告 自动发布展示开始的广告
 *
 * @author andy_hulibo@163.com
 * @date 2020/6/9 23:56
 */
@Configuration
public class AdTimer extends AbstractScheduledTask implements IScheduled {

    private Logger logger= LoggerFactory.getLogger(AdTimer.class);

    @Autowired
    private IAdService adService;

    private final String AD_EXPIRED_CHECK = "ad_expired_check";

    private final String AD_FOR_EXPIRED_CHECK_CRON = "0 10 0 * * ?";

    /**
     * 每天凌晨10分检查广告展示时间状态
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/14 11:32
     */
    @Scheduled(cron = AD_FOR_EXPIRED_CHECK_CRON)
    @ScheduledInfo(name = AD_EXPIRED_CHECK, remark = "每天凌晨10分检查广告展示时间状态")
    public void checkAdsExpired() {
        if (!allowRun(AD_EXPIRED_CHECK)) {
            return;
        }
        String message = "执行成功!";
        try {
            adService.disableExpiredAds();
            adService.enabledUnExpireAds();
        } catch (Exception e) {
            logger.error("检查广告展示时间状态出错",e);
            message = e.getMessage();
        }
        ScheduledTaskExecResult result = new ScheduledTaskExecResult(AD_EXPIRED_CHECK, AD_FOR_EXPIRED_CHECK_CRON, Calendar.getInstance().getTime(), message);
        executeCompletedEvent(new ScheduledTaskExecCompleteEvent(result));
    }
}
