package com.bfly.manage.controller;

import com.bfly.cms.entity.Company;
import com.bfly.cms.service.ICompanyService;
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
 * 企业信息Controller
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/22 11:14
 */
@RestController
@RequestMapping(value = "/manage/company")
public class CompanyController extends BaseController {

    @Autowired
    private ICompanyService companyService;

    /**
     * 查看企业信息
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 11:15
     */
    @GetMapping(value = "/info")
    @ActionModel(value = "查看企业信息", recordLog = false)
    public void viewCompany(HttpServletResponse response) {
        Company company = companyService.getCompany();
        ResponseUtil.writeJson(response, ResponseData.getSuccess(company));
    }

    /**
     * 修改企业信息
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 11:15
     */
    @PostMapping(value = "/edit")
    @ActionModel(value = "修改企业信息")
    public void editCompany(@RequestBody @Valid Company company, BindingResult result, HttpServletResponse response) {
        validData(result);
        companyService.edit(company);
        ContextUtil.setCompanyInfo(company, getRequest().getServletContext());
        ResponseUtil.writeJson(response, ResponseData.getSuccess(""));
    }
}
