package com.bfly.web.directive;

import com.bfly.cms.entity.FriendLink;
import com.bfly.cms.service.IFriendLinkService;
import com.bfly.core.base.action.BaseTemplateDirective;
import com.bfly.core.context.PagerThreadLocal;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 友情链接标签
 *
 * @author andy_hulibo@163.com
 * @date 2020/6/20 10:02
 */
@Component("friendLinkDirective")
public class FriendLinkDirective extends BaseTemplateDirective implements TemplateDirectiveModel {

    @Autowired
    private IFriendLinkService friendLinkService;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        PagerThreadLocal.clear();
        Map<String, Object> param = new HashMap<>(1);
        param.put("enabled", true);

        Map<String, Sort.Direction> sortMap=new HashMap<>();
        sortMap.put("seq", Sort.Direction.DESC);

        List<FriendLink> links = friendLinkService.getList(param,null,sortMap);

        env.setVariable("links", getObjectWrapper().wrap(links));
        body.render(env.getOut());
    }
}
