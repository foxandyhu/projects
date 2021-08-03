package com.bfly.manage.controller;

import com.bfly.cms.entity.SmsProvider;
import com.bfly.cms.service.ISmsRecordService;
import com.bfly.common.DataConvertUtils;
import com.bfly.common.DateUtil;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseController;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.core.security.ActionModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 短信记录管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/19 11:55
 */
@RestController
@RequestMapping(value = "/manage/sms/record")
public class SmsRecordController extends BaseController {

    @Autowired
    private ISmsRecordService smsRecordService;

    /**
     * 短信记录列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/19 11:58
     */
    @GetMapping(value = "/list")
    @ActionModel(value = "短信记录列表", recordLog = false)
    public void listSmsRecord(HttpServletResponse response) {
        PagerThreadLocal.set(getRequest());
        Map<String, Object> exactParams = new HashMap<>(5);
        String beginTimeStr = getRequest().getParameter("beginTime");
        if (StringUtils.isNotBlank(beginTimeStr)) {
            exactParams.put("sendTime_beginDate", DateUtil.parseStrDate(beginTimeStr));
        }
        String endTimeStr = getRequest().getParameter("endTime");
        if (StringUtils.isNotBlank(endTimeStr)) {
            exactParams.put("sendTime_endDate", DateUtil.parseStrDate(endTimeStr));
        }
        String type = getRequest().getParameter("type");
        if (StringUtils.isNotBlank(type)) {
            exactParams.put("type", DataConvertUtils.convertToInteger(type));
        }
        String status = getRequest().getParameter("status");
        if (StringUtils.isNotBlank(status)) {
            exactParams.put("status", DataConvertUtils.convertToInteger(status));
        }
        String providerId = getRequest().getParameter("provider");
        if (StringUtils.isNotBlank(providerId)) {
            SmsProvider provider = new SmsProvider();
            provider.setId(DataConvertUtils.convertToInteger(providerId));
            exactParams.put("provider", provider);
        }

        Map<String, String> unExactParams = new HashMap<>(2);
        String phone = getRequest().getParameter("phone");
        if (StringUtils.isNotBlank(phone)) {
            unExactParams.put("phone", phone);
        }
        String userName = getRequest().getParameter("userName");
        if (StringUtils.isNotBlank(userName)) {
            unExactParams.put("userName", getRequest().getParameter("userName"));
        }

        Pager pager = smsRecordService.getPage(exactParams, unExactParams, null);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(pager));
    }

    /**
     * 短信重发
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/17 13:02
     */
    @GetMapping(value = "/resend-{recordId}")
    @ActionModel(value = "人工重发短信")
    public void resend(@PathVariable("recordId") int recordId, HttpServletResponse response) {
        smsRecordService.resend(recordId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}
