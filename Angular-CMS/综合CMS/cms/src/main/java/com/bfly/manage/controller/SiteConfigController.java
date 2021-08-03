package com.bfly.manage.controller;

import com.alibaba.fastjson.JSONObject;
import com.bfly.cms.entity.SiteConfig;
import com.bfly.cms.service.ISiteConfigService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.core.base.action.BaseController;
import com.bfly.core.context.ContextUtil;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 站点信息Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/18 11:09
 */
@RestController
@RequestMapping(value = "/manage/site")
public class SiteConfigController extends BaseController {

    @Autowired
    private ISiteConfigService siteConfigService;

    /**
     * 查看站点信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 11:10
     */
    @GetMapping(value = "/info")
    @ActionModel(value = "查看站点配置信息", recordLog = false)
    public void viewSiteConfig(HttpServletResponse response) {
        SiteConfig siteConfig = siteConfigService.getSite();
        if (siteConfig == null) {
            siteConfig = new SiteConfig();
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(siteConfig));
    }

    /**
     * 获得站点版权信息
     *
     * @author andy_hulibo@163.com
     * @date 2020/7/1 12:59
     */
    @GetMapping(value = "/copyright")
    @ActionModel(value = "查看站点版权信息", recordLog = false)
    public void getCopyRight(HttpServletResponse response) {
        SiteConfig siteConfig = siteConfigService.getSite();
        if (siteConfig == null) {
            siteConfig = new SiteConfig();
        }
        JSONObject json = new JSONObject();
        json.put("webSite", siteConfig.getWebSite());
        json.put("name", siteConfig.getName());
        json.put("shortName", siteConfig.getShortName());
        json.put("logo", siteConfig.getLogo());
        json.put("filingCode", siteConfig.getFilingCode());
        json.put("copyRight", siteConfig.getCopyRight());
        json.put("copyRightOwner", siteConfig.getCopyRightOwner());
        json.put("organizer", siteConfig.getOrganizer());
        ResponseUtil.writeJson(response, ResponseData.getSuccess(siteConfig));
    }

    /**
     * 修改站点信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/18 11:14
     */
    @PostMapping(value = "/edit")
    @ActionModel(value = "修改站点配置信息")
    public void editSiteConfig(@RequestBody @Valid SiteConfig siteConfig, BindingResult result, HttpServletResponse response) {
        validData(result);
        siteConfigService.edit(siteConfig);
        ContextUtil.setSiteConfig(siteConfig, getRequest().getServletContext());
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}
