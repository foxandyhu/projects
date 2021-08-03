package com.bfly.web.directive;

import com.bfly.cms.entity.Article;
import com.bfly.cms.enums.ArticleStatus;
import com.bfly.cms.service.IArticleService;
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
 * 文章详情标签
 *
 * @author andy_hulibo@163.com
 * @date 2019/9/3 12:23
 */
@Component("articleDirective")
public class ArticleDirective extends BaseTemplateDirective implements TemplateDirectiveModel {

    @Autowired
    private IArticleService articleService;

    /**
     * 查询条件 articleId 文章ID
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/3 12:29
     */
    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        String articleId = "articleId";
        Integer aId = getData(articleId, params, Integer.class);
        Article article = articleService.get(aId);

        if (article == null || article.getStatus() != ArticleStatus.PASSED.getId()) {
            article = null;
        }

        env.setVariable("article", getObjectWrapper().wrap(article));
        body.render(env.getOut());
    }
}
