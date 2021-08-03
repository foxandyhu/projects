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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 网站栏目标签
 *
 * @author andy_hulibo@163.com
 * @date 2020/6/19 18:12
 */
@Component("channelListDirect")
public class ChannelsDirective extends BaseTemplateDirective implements TemplateDirectiveModel {

    @Autowired
    private IChannelService channelService;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        Map<String, Object> param = new HashMap<>(1);
        param.put("parentId", 0);
        List<Channel> list = channelService.getList(param);
        list = filterDisplay(list);
        Collections.sort(list);
        env.setVariable("channels", getObjectWrapper().wrap(list));
        body.render(env.getOut());
    }

    private List<Channel> filterDisplay(List<Channel> list) {
        list = list.stream().filter(channel -> {
            if (channel.getChildren().size() > 0) {
                channel.setChildren(filterDisplay(channel.getChildren()));
            }
            return channel.isDisplay();
        }).collect(Collectors.toList());
        return list;
    }
}
