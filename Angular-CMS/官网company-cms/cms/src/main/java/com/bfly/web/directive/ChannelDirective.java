package com.bfly.web.directive;

import com.bfly.cms.entity.Channel;
import com.bfly.cms.service.IChannelService;
import com.bfly.core.base.action.BaseTemplateDirective;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 栏目信息标签
 *
 * @author andy_hulibo@163.com
 * @date 2020/6/19 18:12
 */
@Component("channelDirect")
public class ChannelDirective extends BaseTemplateDirective implements TemplateDirectiveModel {

    @Autowired
    private IChannelService channelService;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        Integer channelId = getData("channelId", params, Integer.class);
        Channel channel = channelService.get(channelId);

        env.setVariable("channel", getObjectWrapper().wrap(channel));
        body.render(env.getOut());
    }
}
