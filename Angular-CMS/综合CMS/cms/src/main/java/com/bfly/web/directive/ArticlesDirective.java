package com.bfly.web.directive;

import com.bfly.cms.entity.Article;
import com.bfly.cms.entity.Channel;
import com.bfly.cms.enums.ArticleStatus;
import com.bfly.cms.service.IArticleService;
import com.bfly.cms.service.IChannelService;
import com.bfly.common.page.Pager;
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
 * 文章集合标签
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/3 12:23
 */
@Component("articleListDirective")
public class ArticlesDirective extends BaseTemplateDirective implements TemplateDirectiveModel {

    @Autowired
    private IArticleService articleService;
    @Autowired
    private IChannelService channelService;

    /**
     * 查询条件 pageSize 返回条数 默认是10条 recommend 是否推荐  channelId 栏目ID
     * desc asc排序 格式：desc=id,seq 根据id和seq属性降序 asc=id,seq根据id和seq属性升序
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/3 12:29
     */
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String pageSize = "pageSize", pageNo = "pageNo", recommend = "recommend", channelId = "channelId", desc = "desc", asc = "asc";
        Map<String, Object> queryParam = new HashMap<>(2);
        int size = 10;
        int no = 1;
        if (params.containsKey(pageSize)) {
            size = getData(pageSize, params, Integer.class);
        }
        if (params.containsKey(pageNo)) {
            no = getData(pageNo, params, Integer.class);
        }
        Pager pager = new Pager(no, size, Integer.MAX_VALUE);
        PagerThreadLocal.set(pager);

        if (params.containsKey(recommend)) {
            queryParam.put(recommend, getData(recommend, params, Boolean.class));
        }

        Channel channel = null;
        if (params.containsKey(channelId)) {
            Integer cId = getData(channelId, params, Integer.class);
            queryParam.put(channelId, cId);

            channel = channelService.get(cId);
        }

        queryParam.put("status", ArticleStatus.PASSED.getId());

        Map<String, Sort.Direction> sortMap = new HashMap<>(2);
        if (params.containsKey(desc)) {
            String descProp = getData(desc, params, String.class);
            String props[] = descProp.split(",");
            for (String prop : props) {
                sortMap.put(prop, Sort.Direction.DESC);
            }
        }
        if (params.containsKey(asc)) {
            String ascProp = getData(asc, params, String.class);
            String props[] = ascProp.split(",");
            for (String prop : props) {
                sortMap.put(prop, Sort.Direction.ASC);
            }
        }

        pager = articleService.getPage(queryParam, null, sortMap);
        PagerThreadLocal.clear();

        List<Article> list = pager.getData();
        pager = new Pager(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());

        env.setVariable("articles", getObjectWrapper().wrap(list));
        env.setVariable("pager", getObjectWrapper().wrap(pager));
        if (channel != null) {
            env.setVariable("channel", getObjectWrapper().wrap(channel));
        }
        body.render(env.getOut());
    }
}
