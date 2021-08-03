package com.bfly.web.directive;

import com.bfly.cms.entity.Ad;
import com.bfly.cms.enums.AdStatusEnum;
import com.bfly.cms.service.IAdService;
import com.bfly.core.base.action.BaseTemplateDirective;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 广告标签
 * 给定指定广告位ID 显示广告
 *
 * @author andy_hulibo@163.com
 * @date 2019/8/30 20:21
 */
@Component("adDirective")
public class AdDirective extends BaseTemplateDirective implements TemplateDirectiveModel {

    @Autowired
    private IAdService adService;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String adSpaceId = "spaceId";
        if (!params.containsKey(adSpaceId)) {
            return;
        }
        Integer id = getData(adSpaceId, params, Integer.class);
        if (id == null) {
            return;
        }
        Map<String, Object> param = new HashMap<>(1);
        param.put("spaceId", id);
        param.put("status", AdStatusEnum.PUBLISHING.getId());
        param.put("enabled", true);
        List<Ad> list = adService.getList(param);
        env.setVariable("ads", getObjectWrapper().wrap(list));
        body.render(env.getOut());
    }
}
