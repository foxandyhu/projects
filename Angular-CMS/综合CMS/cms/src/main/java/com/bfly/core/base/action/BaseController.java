package com.bfly.core.base.action;

import com.bfly.cms.entity.Member;
import com.bfly.cms.entity.User;
import com.bfly.cms.enums.SysError;
import com.bfly.common.FileUtil;
import com.bfly.core.config.ResourceConfigure;
import com.bfly.core.context.ContextUtil;
import com.bfly.core.exception.WebPageNotFoundException;
import com.bfly.core.exception.WebResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;

/**
 * 后台管理接口父类Controller
 *
 * @author andy_hulibo@163.com
 * @date 2018/12/3 16:44
 */
public class BaseController extends AbstractController {

    private Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 获得用户
     *
     * @return 用户
     * @author andy_hulibo@163.com
     * @date 2018/11/29 14:53
     */
    public Member getMember() {
        return ContextUtil.getLoginMember();
    }

    /**
     * 获得当前登录管理员对象
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/3 20:36
     */
    public User getUser() {
        return ContextUtil.getLoginUser();
    }

    /**
     * URL重定向
     *
     * @author andy_hulibo@163.com
     * @date 2018/11/29 16:59
     */
    public String redirect(String url) {
        return "redirect:" + url;
    }

    /**
     * 校验数据
     *
     * @author andy_hulibo@163.com
     * @date 2018/12/10 12:50
     */
    public void validData(BindingResult result) {
        if (result.hasErrors()) {
            throw new WebResponseException(SysError.PARAM_ERROR, result.getFieldError().getDefaultMessage());
        }
    }

    /**
     * 得到渲染模板路径
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/18 16:01
     */
    public String renderTplPath(String tplPath) {
        if (!StringUtils.hasLength(tplPath)) {
            throw new WebPageNotFoundException();
        }
        if (!FileUtil.checkExist(ResourceConfigure.getTemplateAbsolutePath(tplPath))) {
            throw new WebPageNotFoundException();
        }
        return tplPath;
    }
}
