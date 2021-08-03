package com.bfly.web.directive;

import com.bfly.common.StringUtil;
import freemarker.template.TemplateMethodModelEx;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 去除HTML标签
 * @author andy_hulibo@163.com
 * @date 2020/6/20 18:38
 */
@Component("removeHtmlTagMethod")
public class RemoveHtmlTagMethod implements TemplateMethodModelEx {

    @Override
    public Object exec(List arguments) {
        String str = String.valueOf(arguments.get(0));
        return StringUtil.trimHtmlTag(str);
    }
}
