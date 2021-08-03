package com.bfly.web.directive;

import com.bfly.cms.entity.City;
import com.bfly.cms.service.ICityService;
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
 * 城市标签
 * 给定parentId 显示子类城市
 *
 * @author andy_hulibo@163.com
 * @date 2020/8/26 14:18
 */
@Component("cityDirective")
public class CityDirective extends BaseTemplateDirective implements TemplateDirectiveModel {

    @Autowired
    private ICityService cityService;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String parentId = "parentId";
        if (!params.containsKey(parentId)) {
            return;
        }
        Integer id = getData(parentId, params, Integer.class);
        if (id == null) {
            return;
        }
        Map<String, Object> param = new HashMap<>(1);
        param.put("parentId", id);
        List<City> list = cityService.getList(param);
        env.setVariable("cities", getObjectWrapper().wrap(list));
        body.render(env.getOut());
    }
}
