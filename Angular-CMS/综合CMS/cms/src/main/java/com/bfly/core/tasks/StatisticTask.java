package com.bfly.core.tasks;

import com.bfly.cms.service.ISiteAccessService;
import com.bfly.cms.service.ISiteAccessStatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Calendar;

/**
 * 统计定时任务类
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/27 12:40
 */
@Configuration
public class StatisticTask extends AbstractScheduledTask implements IScheduled {

    private Logger logger= LoggerFactory.getLogger(StatisticTask.class);

    @Autowired
    private ISiteAccessService accessService;
    @Autowired
    private ISiteAccessStatisticService statisticService;

    private static final String ACCESS_STATISTIC_CACHE_TO_DB = "access_statistic_cache_to_db";
    private static final String ACCESS_STATISTIC_REPORT_PRE_DAYS = "access_statistic_report_pre_days";
    private static final String FLUSH_CACHE_TO_DB_CRON="0 0/10 * * * ?";
     private static final String REPORT_FOR_PRE_DAYS_CRON="0 0 0 * * ?";

    /**
     * 每10分钟一次刷新站点访问缓存流量数据到数据库
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/27 13:56
     */
    @Scheduled(cron =FLUSH_CACHE_TO_DB_CRON)
    @ScheduledInfo(name = ACCESS_STATISTIC_CACHE_TO_DB, remark = "每10分钟一次刷新站点访问缓存流量数据到数据库")
    public void accessStatisticFlushCacheToDB() {
        //开启状态的任务才能执行
        if (!allowRun(ACCESS_STATISTIC_CACHE_TO_DB)) {
            return;
        }
        String message = "执行成功!";
        try {
            accessService.siteAccessCacheDataToDb();
        } catch (Exception e) {
            logger.error("刷新站点访问数据到数据库出错",e);
            message = e.getMessage();
        }
        ScheduledTaskExecResult result = new ScheduledTaskExecResult(ACCESS_STATISTIC_CACHE_TO_DB, FLUSH_CACHE_TO_DB_CRON, Calendar.getInstance().getTime(), message);
        executeCompletedEvent(new ScheduledTaskExecCompleteEvent(result));
    }

    /**
     * 每天凌晨0点统计昨日的所有流量报表
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/27 13:56
     */
    @Scheduled(cron =REPORT_FOR_PRE_DAYS_CRON)
    @ScheduledInfo(name = ACCESS_STATISTIC_REPORT_PRE_DAYS, remark = "每天0点根据不同维度统计今日之前的流量报表")
    public void accessStatisticReportForPreDays() {
        if (!allowRun(ACCESS_STATISTIC_REPORT_PRE_DAYS)) {
            return;
        }
        String message = "执行成功!";
        try {
            statisticService.analysisAndStatisticAccessDataToReport();
        } catch (Exception e) {
            logger.error("生成流量报表出错",e);
            message = e.getMessage();
        }
        ScheduledTaskExecResult result = new ScheduledTaskExecResult(ACCESS_STATISTIC_REPORT_PRE_DAYS, REPORT_FOR_PRE_DAYS_CRON, Calendar.getInstance().getTime(), message);
        executeCompletedEvent(new ScheduledTaskExecCompleteEvent(result));
    }
}
