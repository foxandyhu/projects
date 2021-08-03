package com.bfly.web.controller;

import com.bfly.cms.entity.SiteConfig;
import com.bfly.cms.service.ISiteConfigService;
import com.bfly.cms.service.IStatisticService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.core.base.action.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 网站CommonController
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/29 10:29
 */
@Controller
public class CommonController extends BaseController {

    @Autowired
    private ISiteConfigService siteConfigService;
    @Autowired
    private IStatisticService statisticService;

    /**
     * 站点关闭
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/20 15:29
     */
    @GetMapping(value = "/close")
    public String siteClose(HttpServletRequest request, HttpServletResponse response) {
        SiteConfig config = siteConfigService.getSite();
        if (config.isOpenSite()) {
            try {
                response.sendRedirect("/");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }
        return "site_close.html";
    }

    /**
     * 404错误
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/20 14:10
     */
    @GetMapping(value = "/404")
    public String pageNotFound() {
        return "404.html";
    }

    /**
     * 500错误
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/20 14:10
     */
    @GetMapping(value = "/500")
    public String serverInnerError() {
        return "500.html";
    }


    /**
     * 站点访问统计
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/25 23:06
     */
    @GetMapping(value = "/statistic")
    public void siteAccessStatistic(HttpServletRequest request, HttpServletResponse response) throws Exception {
        SiteConfig config = siteConfigService.getSite();
        if (config.isOpenFlow()) {
            statisticService.flow();
        }
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}
