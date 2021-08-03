package com.bfly.web.controller;

import com.bfly.cms.entity.Channel;
import com.bfly.cms.entity.City;
import com.bfly.cms.entity.SiteConfig;
import com.bfly.cms.enums.CityTypeEnum;
import com.bfly.cms.service.IArticleService;
import com.bfly.cms.service.IChannelService;
import com.bfly.cms.service.ICityService;
import com.bfly.common.IDEncrypt;
import com.bfly.core.base.action.BaseController;
import com.bfly.core.context.ContextUtil;
import com.bfly.core.exception.WebPageNotFoundException;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 网站首页Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/29 10:29
 */
@Controller
public class IndexController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(com.bfly.web.controller.IndexController.class);

    @Autowired
    private IChannelService channelService;
    @Autowired
    private IArticleService articleService;
    @Autowired
    private ICityService cityService;

    /**
     * 站点首页
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/21 15:20
     */
    @GetMapping(value = {"/", "/index.html"})
    public String index() {
        SiteConfig config = ContextUtil.getSiteConfig(getRequest().getServletContext());
        String tpl = isMobileRequest() ? config.getTplMobile() : config.getTplPc();
        if (!StringUtils.hasLength(tpl)) {
            //如果没有设置站点首页,则默认首页是模板根路径下的index.html
            tpl = "index.html";
        }
        return renderTplPath(tpl);
    }

    /**
     * 栏目页
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/19 16:25
     */
    @GetMapping(value = "/{channelPath}")
    public String channelPage(@PathVariable("channelPath") String path) {
        path = "/" + path + ".html";
        Channel channel = channelService.getChannelByPath(path);
        if (channel == null || !channel.isDisplay()) {
            throw new WebPageNotFoundException();
        }
        String tpl = isMobileRequest() ? channel.getTplMobileChannel() : channel.getTplPcChannel();
        getRequest().setAttribute("channel", channel);
        return renderTplPath(tpl);
    }

    /**
     * 资讯详情
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/20 19:42
     */
    @GetMapping(value = "/{alias}/id_{idStr}.html")
    public String detail(HttpServletRequest request, HttpServletResponse response, @PathVariable("alias") String alias, @PathVariable("idStr") String idStr) {
        Channel channel = channelService.getChannelByAlias(alias);
        if (channel == null || !channel.isDisplay()) {
            throw new WebPageNotFoundException();
        }
        long id = 0;
        try {
            id = IDEncrypt.decode(idStr);
        } catch (Exception e) {
            throw new WebPageNotFoundException();
        }
        articleService.incrementArticleViews((int) id, 1);
        String tpl = isMobileRequest() ? channel.getTplMobileContent() : channel.getTplPcContent();
        request.setAttribute("channel", channel);
        request.setAttribute("articleId", id);
        return renderTplPath(tpl);
    }

    /**
     * 子站首页
     *
     * @author andy_hulibo@163.com
     * @date 2020/8/26 11:00
     */
    @GetMapping(value = "/site/{pinyin}")
    public String subSiteIndex(@PathVariable("pinyin") String pinyin) {
        City city = getSubSiteCity(pinyin);
        getRequest().setAttribute("city", city);
        return renderTplPath("subsite/index.html");
    }

    /**
     * 子站字页
     * @author andy_hulibo@163.com
     * @date 2020/8/26 21:17
     */
    @GetMapping(value = "/site/{pinyin}/{page}")
    public String subSitePage(@PathVariable("pinyin") String pinyin,@PathVariable("page")String page) {
        City city = getSubSiteCity(pinyin);
        getRequest().setAttribute("city", city);
        return renderTplPath("subsite/"+page+".html");
    }

    /**
     * 子站资讯详情
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/20 19:42
     */
    @GetMapping(value = "site/{pinyin}/id_{idStr}.html")
    public String subSiteDetail(@PathVariable("pinyin") String pinyin, @PathVariable("idStr") String idStr) {
        City city = getSubSiteCity(pinyin);

        long id = 0;
        try {
            id = IDEncrypt.decode(idStr);
        } catch (Exception e) {
            throw new WebPageNotFoundException();
        }
        articleService.incrementArticleViews((int) id, 1);
        getRequest().setAttribute("city", city);
        getRequest().setAttribute("articleId", id);
        return renderTplPath("subsite/news_detail.html");
    }

    /**
     * 获得子站城市名称
     * @author andy_hulibo@163.com
     * @date 2020/8/28 9:45
     */
    private City getSubSiteCity(String pinyin){
        List<City> cities = cityService.getCity(pinyin, CityTypeEnum.CITY.getId());
        City city = CollectionUtils.isNotEmpty(cities) ? cities.get(0) : null;
        if(city==null){
            city=new City();
            city.setPinyin("index");
        }
        return city;
    }
}
