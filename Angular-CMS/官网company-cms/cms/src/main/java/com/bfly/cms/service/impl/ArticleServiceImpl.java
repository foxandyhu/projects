package com.bfly.cms.service.impl;

import com.bfly.cms.dao.IArticleDao;
import com.bfly.cms.dao.IArticleExtDao;
import com.bfly.cms.dao.IArticleTxtDao;
import com.bfly.cms.entity.*;
import com.bfly.cms.entity.dto.ArticleLuceneDTO;
import com.bfly.cms.enums.ArticleStatus;
import com.bfly.cms.enums.ContentType;
import com.bfly.cms.service.IArticleService;
import com.bfly.cms.service.IChannelService;
import com.bfly.cms.service.ILuceneService;
import com.bfly.common.DateUtil;
import com.bfly.common.IDEncrypt;
import com.bfly.common.page.Pager;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.*;

/**
 * @author andy_hulibo@163.com
 * @date 2019/8/1 13:45
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class ArticleServiceImpl extends BaseServiceImpl<Article, Integer> implements IArticleService {

    private Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Autowired
    private IArticleDao articleDao;
    @Autowired
    private IArticleExtDao extDao;
    @Autowired
    private IArticleTxtDao txtDao;
    @Autowired
    private IChannelService channelService;
    @Autowired
    private ILuceneService luceneService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveRelatedSpecialTopic(Collection<? extends Integer> articleIds, Collection<? extends Integer> topicIds) {
        if (articleIds != null && topicIds != null) {
            articleIds.forEach(articleId -> {
                topicIds.forEach(topicId -> {
                    //查询是否存在关联 不存在则添加关联
                    boolean exist = articleDao.isExistSpecialTopicArticleShip(articleId, topicId);
                    if (!exist) {
                        articleDao.saveSpecialTopicArticleShip(articleId, topicId);
                    }
                });
            });
            getCache().clear();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeRelatedSpecialTopic(Integer articleId, Integer topicId) {
        articleDao.removeSpecialTopicArticleShip(articleId, topicId);
        getCache().clear();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int remove(Integer... integers) {
        if (integers != null) {
            for (Integer id : integers) {
                if (id != null) {
                    //清除文章的评分记录
                    articleDao.removeArticleScore(id);
                    //清除文章和专题的关联
                    articleDao.clearSpecialTopicArticleShip(id);

                    //删除索引库
                    luceneService.deleteIndex(id);
                }
            }
        }
        return super.remove(integers);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verifyArticle(ArticleStatus status, Integer... articleIds) {
        if (articleIds == null || articleIds.length == 0) {
            return;
        }
        if (status == ArticleStatus.PASSED) {
            Integer[] ids = new Integer[0];
            // 发布日期大于当前日期的文章 审核通过状态 会重置为发布中状态
            Article article;
            Integer[] passingIds = new Integer[0];
            for (Integer articleId : articleIds) {
                article = get(articleId);
                if (article != null && DateUtil.formatterDate(article.getArticleExt().getPostDate()).after(DateUtil.formatterDate(new Date()))) {
                    passingIds = ArrayUtils.add(passingIds, articleId);
                } else {
                    ids = ArrayUtils.add(ids, articleId);
                }
            }
            if (passingIds.length > 0) {
                articleDao.verifyArticle(ArticleStatus.PASSING.getId(), passingIds);
            }
            articleDao.verifyArticle(ArticleStatus.PASSED.getId(), ids);
        } else {
            articleDao.verifyArticle(status.getId(), articleIds);
        }

        getCache().clear();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recommendArticle(boolean recommend, Integer... articleIds) {
        articleDao.recommendArticle(recommend, articleIds);

        getCache().clear();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editArticleTop(int articleId, int topLevel, Date expired) {
        //不置顶 有效期则为null
        if (topLevel == 0) {
            expired = null;
        }
        articleDao.editArticleTop(articleId, topLevel, expired);

        getCache().clear();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Article article) {
        verify(article);

        //新增文章默认数据
        article.setViews(0);
        article.setDownloads(0);
        article.setUps(0);
        article.setDowns(0);
        article.setComments(0);

        ArticleExt ext = article.getArticleExt();
        ArticleTxt txt = article.getArticleTxt();

        article.setArticleExt(null);
        article.setArticleTxt(null);
        boolean flag = super.save(article);
        if (flag) {
            ext.setArticleId(article.getId());
            txt.setArticleId(article.getId());
            extDao.save(ext);
            txtDao.save(txt);
            luceneService.createIndex(article);
        }
        return flag;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(Article article) {
        Article dbArticle = get(article.getId());
        Assert.notNull(dbArticle, "不存在的文章对象!");
        verify(article);

        article.setChannelId(dbArticle.getChannelId());

        ArticleExt ext = article.getArticleExt();
        ArticleTxt txt = article.getArticleTxt();

        article.setArticleExt(null);
        article.setArticleTxt(null);

        boolean flag = super.edit(article);
        if (flag) {
            ext.setArticleId(article.getId());
            txt.setArticleId(article.getId());
            extDao.save(ext);
            txtDao.save(txt);
            luceneService.createIndex(article);
        }
        return flag;
    }

    /**
     * 校验
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/8 14:47
     */
    private Article verify(Article article) {
        ContentType contentType = ContentType.getType(article.getType());
        if (contentType == null) {
            //默认为普通的内容类型
            article.setType(ContentType.NORMAL.getId());
        }

        ArticleExt ext = article.getArticleExt();
        if (ext != null) {
            //如果发布日期没有指定则默认为当前日期
            if (ext.getPostDate() == null) {
                ext.setPostDate(new Date());
            }
        }

        ArticleStatus status = ArticleStatus.getStatus(article.getStatus());
        if (status == null) {
            //默认为审核通过状态
            article.setStatus(ArticleStatus.PASSED.getId());
        }

        if (article.getStatus() == ArticleStatus.PASSED.getId()) {
            if (ext.getPostDate().after(new Date())) {
                //  发布日期大于当前日期并且状态是审核通过状态 则重置为发布中状态,系统会根据发布日期自动发布文章
                article.setStatus(ArticleStatus.PASSING.getId());
            }
        }
        if (article.getStatus() == ArticleStatus.PASSING.getId()) {
            if (ext.getPostDate().before(new Date())) {
                //  发布日期小于当前日期并且状态是发布中状态 则重置为审核通过状态
                article.setStatus(ArticleStatus.PASSED.getId());
            }
        }

        if (article.getTopLevel() <= 0) {
            //如果置顶等级为0 则设置失效日期无效
            article.setTopLevel(0);
            article.setTopExpired(null);
        }

        Channel channel = channelService.get(article.getChannelId());
        Assert.notNull(channel, "未指定文章所属栏目!");
        return article;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delArticlePicture(int picId) {
        articleDao.delArticlePicture(picId);
        getCache().clear();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delArticleAttachment(int attachmentId) {
        articleDao.delArticleAttachment(attachmentId);
        getCache().clear();
    }

    @Override
    public Map<String, BigInteger> getTodayAndTotalArticle() {
        return articleDao.getTodayAndTotalArticle();
    }

    @Override
    public List<Map<String, Object>> getClickTopArticle(int limit) {
        return articleDao.getClickTopArticle(limit);
    }

    @Override
    public List<Map<String, Object>> getCommentsTopArticle(int limit) {
        return articleDao.getCommentsTopArticle(limit);
    }

    @Override
    public Map<String, Object> getNext(int currentArticleId, Integer channelId) {
        Map<String, Object> map = articleDao.getNext(currentArticleId, channelId, ArticleStatus.PASSED.getId());
        return mapConvert(map);
    }

    @Override
    public Map<String, Object> getPrev(int currentArticleId, Integer channelId) {
        Map<String, Object> map = articleDao.getPrev(currentArticleId, channelId, ArticleStatus.PASSED.getId());
        return mapConvert(map);
    }

    private Map<String, Object> mapConvert(Map<String, Object> map) {
        Map<String, Object> result = null;
        if (MapUtils.isNotEmpty(map)) {
            result = new HashMap<>(map.size());
            result.putAll(map);
            result.put("idStr", IDEncrypt.encode((int) map.get("id")));
            result.remove("id");
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incrementArticleComments(int articleId, int setup) {
        int count = articleDao.editArticleComments(articleId, setup);
        if (count > 0) {
            Article article = getCache().get(articleId, Article.class);
            if (article != null) {
                article.setComments(article.getComments() + setup);
                getCache().put(article, article);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incrementArticleViews(int articleId, int setup) {
        int count = articleDao.editArticleViews(articleId, setup);

        if (count > 0) {
            Article article = getCache().get(articleId, Article.class);
            if (article != null) {
                article.setViews(article.getViews() + setup);
                getCache().put(article, article);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void incrementArticleDownloads(int articleId, int setup) {
        int count = articleDao.editArticleDownloads(articleId, setup);
        if (count > 0) {
            Article article = getCache().get(articleId, Article.class);
            if (article != null) {
                article.setDownloads(article.getDownloads() + setup);
                getCache().put(article, article);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int resetArticleTopLevelForExpired(Date date) {
        return articleDao.resetArticleTopLevelForExpired(date);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int autoPublishArticle() {
        return articleDao.autoPublishArticle(ArticleStatus.PASSED.getId(), ArticleStatus.PASSING.getId());
    }

    @Override
    @Async
    public void resetArticleIndex() {
        boolean flag = luceneService.deleteAll();
        if (flag) {
            long total = getCount();
            int pageSize = 100;
            long totalPage = (total + pageSize - 1) / pageSize;
            logger.info("开始重构索引库!");
            for (int i = 0; i < totalPage; i++) {
                List<Map<String, Object>> list = articleDao.getArticleLuceneDTO(i * pageSize, pageSize);
                ArticleLuceneDTO[] articles = convertToArticleLucene(list);

                long begin = System.currentTimeMillis();
                flag = luceneService.createIndex(articles);
                long end = System.currentTimeMillis();
                if (flag) {
                    logger.info("成功建立索引" + articles.length + "条,耗时" + (end - begin) / 1000 + "秒!");
                } else {
                    logger.warn("重构索引库失败!");
                }
            }
            logger.info("结束重构索引库!");
        }
    }

    private ArticleLuceneDTO[] convertToArticleLucene(List<Map<String, Object>> list) {
        if (list == null) {
            return new ArticleLuceneDTO[0];
        }
        ArticleLuceneDTO[] articles = new ArticleLuceneDTO[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ArticleLuceneDTO dto = new ArticleLuceneDTO();
            Map<String, Object> map = list.get(i);
            dto.setId((int) map.get("id"));
            dto.setStatus((int) map.get("status"));
            dto.setTitle((String) map.get("title"));
            dto.setTitleImg((String) map.get("titleImg"));
            dto.setChannelPath((String) map.get("channelPath"));
            dto.setTxt((String) map.get("txt"));
            dto.setSummary((String) map.get("summary"));
            dto.setPostDate((Date) map.get("postDate"));
            articles[i] = dto;
        }
        return articles;
    }

    @Override
    public Pager<ArticleLuceneDTO> searchFromIndex(String keyWord) {
        if (!StringUtils.hasLength(keyWord)) {
            return null;
        }
        return luceneService.query(keyWord);
    }


}