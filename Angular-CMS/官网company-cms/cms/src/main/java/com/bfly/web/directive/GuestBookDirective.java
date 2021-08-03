package com.bfly.web.directive;

import com.bfly.cms.entity.GuestBook;
import com.bfly.cms.service.IGuestBookService;
import com.bfly.common.page.Pager;
import com.bfly.core.base.action.BaseTemplateDirective;
import com.bfly.core.context.PagerThreadLocal;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 留言标签
 *
 * @author andy_hulibo@163.com
 * @date 2020/12/21 20:22
 */
@Component("guestBookDirective")
public class GuestBookDirective extends BaseTemplateDirective implements TemplateDirectiveModel {

    @Autowired
    private IGuestBookService guestBookService;

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String statusStr = "status", typeStr = "type", pageNoStr = "pageNo", pageSizeStr = "pageSize";
        Map<String, Object> param = new HashMap<>(2);
        if (params.containsKey(statusStr)) {
            int status = getData(statusStr, params, Integer.class);
            param.put("status", status);
        }
        if (params.containsKey(typeStr)) {
            int type = getData(typeStr, params, Integer.class);
            param.put("type", type);
        }
        int pageNo = 1, pageSize = 10;
        if (params.containsKey(pageNoStr)) {
            pageNo = getData(pageNoStr, params, Integer.class);
            pageNo = pageNo <= 0 ? 1 : pageNo;
        }
        if (params.containsKey(pageSizeStr)) {
            pageSize = getData(pageSizeStr, params, Integer.class);
            pageSize = pageSize <= 0 ? 1 : pageSize;
        }
        PagerThreadLocal.set(new Pager(pageNo, pageSize, Integer.MAX_VALUE));
        Pager<GuestBook> pager = guestBookService.getPage(param);
        env.setVariable("guestBookPager", getObjectWrapper().wrap(pager));
        body.render(env.getOut());
    }
}
