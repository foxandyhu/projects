package com.bfly.manage.controller;

import com.bfly.cms.entity.Ad;
import com.bfly.cms.entity.AdSpace;
import com.bfly.cms.service.IAdService;
import com.bfly.cms.service.IAdSpaceService;
import com.bfly.common.ResponseData;
import com.bfly.common.ResponseUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseController;
import com.bfly.core.context.PagerThreadLocal;
import com.bfly.cms.enums.SysError;
import com.bfly.core.exception.WsResponseException;
import com.bfly.core.security.ActionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 广告管理Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/11 16:53
 */
@RestController
@RequestMapping(value = "/manage/ad")
public class AdController extends BaseController {

    @Autowired
    private IAdService adService;
    @Autowired
    private IAdSpaceService spaceService;

    /**
     * 广告列表
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 16:53
     */
    @GetMapping("/list")
    @ActionModel(value = "广告列表", recordLog = false)
    public void listAd(HttpServletRequest request, HttpServletResponse response) {
        PagerThreadLocal.set(request);
        Pager pager = adService.getPage(null);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(pager));
    }

    /**
     * 新增广告
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 16:57
     */
    @PostMapping(value = "/add")
    @ActionModel("新增广告")
    public void addAd(@RequestBody @Valid Ad ad, BindingResult result, HttpServletResponse response) {
        validData(result);
        adService.save(ad);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 修改广告
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:01
     */
    @PostMapping(value = "/edit")
    @ActionModel("修改广告")
    public void editAd(@RequestBody @Valid Ad ad, BindingResult result, HttpServletResponse response) {
        validData(result);
        adService.edit(ad);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }

    /**
     * 根据广告位获取广告
     *
     * @author andy_hulibo@163.com
     * @date 2020/4/11 23:17
     */
    @GetMapping(value = "/space-{adSpaceId}")
    @ActionModel(recordLog = false)
    public void getAds(@PathVariable("adSpaceId") int adSpaceId, HttpServletResponse response) {
        AdSpace space = spaceService.get(adSpaceId);
        if (space == null) {
            throw new WsResponseException(SysError.MISSING_PARAM, null);
        }
        if (!space.isEnabled()) {
            throw new WsResponseException(SysError.ERROR, "广告位未启用");
        }
        Map<String, Object> param = new HashMap<>(1);
        param.put("spaceId", adSpaceId);
        param.put("enabled", true);
        List<Ad> list = adService.getList(param);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(list));
    }

    /**
     * 获取广告基本信息
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:02
     */
    @GetMapping(value = "/{adId}")
    @ActionModel(value = "广告详情", recordLog = false)
    public void viewAd(@PathVariable("adId") int adId, HttpServletResponse response) {
        Ad ad = adService.get(adId);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(ad));
    }

    /**
     * 删除广告
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/11 17:02
     */
    @PostMapping(value = "/del")
    @ActionModel("删除广告")
    public void removeAd(HttpServletResponse response, @RequestBody Integer... ids) {
        adService.remove(ids);
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}
