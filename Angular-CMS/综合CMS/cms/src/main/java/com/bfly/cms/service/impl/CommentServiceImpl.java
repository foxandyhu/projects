package com.bfly.cms.service.impl;

import com.bfly.cms.dao.ICommentDao;
import com.bfly.cms.dao.ICommentTxtDao;
import com.bfly.cms.entity.Article;
import com.bfly.cms.entity.Comment;
import com.bfly.cms.entity.CommentTxt;
import com.bfly.cms.entity.Member;
import com.bfly.cms.enums.ArticleStatus;
import com.bfly.cms.enums.CommentStatus;
import com.bfly.cms.enums.ContentType;
import com.bfly.cms.service.IArticleService;
import com.bfly.cms.service.ICommentService;
import com.bfly.common.IDEncrypt;
import com.bfly.common.ipseek.IPLocation;
import com.bfly.common.ipseek.IpSeekerUtil;
import com.bfly.common.page.Pager;
import com.bfly.core.base.service.impl.BaseServiceImpl;
import com.bfly.core.cache.EhCacheUtil;
import com.bfly.core.context.IpThreadLocal;
import com.bfly.core.context.MemberThreadLocal;
import com.bfly.core.context.PagerThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.math.BigInteger;
import java.util.*;

/**
 * @author andy_hulibo@163.com
 * @date 2018/12/12 11:37
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
public class CommentServiceImpl extends BaseServiceImpl<Comment, Integer> implements ICommentService {

    @Autowired
    private ICommentDao commentDao;
    @Autowired
    private ICommentTxtDao commentTxtDao;
    @Autowired
    private IArticleService articleService;

    @Override
    public Pager getPage(Map<String, Object> property) {
        Pager pager = PagerThreadLocal.get();
        Assert.notNull(pager, "分页器没有实例化");

        Pageable pageable = getPageRequest(pager);
        Page<Map<String, Object>> page = commentDao.getCommentPage((Integer) property.get("status"), (Boolean) property.get("recommend"), (Integer) property.get("articleId"), pageable);
        List<Map<String, Object>> list = new ArrayList<>();
        if (page.getContent() != null) {
            String status = "status";
            String typeId = "typeId";
            page.getContent().forEach(map -> {
                Map<String, Object> dataMap = new HashMap<>(map.size());
                dataMap.putAll(map);
                if (map.containsKey(status)) {
                    int statusId = (int) map.get(status);
                    CommentStatus commentStatus = CommentStatus.getStatus(statusId);
                    dataMap.put("statusName", commentStatus == null ? "" : commentStatus.getName());
                }
                if (map.containsKey(typeId)) {
                    Integer type = (Integer) map.get(typeId);
                    ContentType contentType = ContentType.getType(type == null ? 0 : type);
                    dataMap.put("typeName", contentType == null ? "" : contentType.getName());
                }
                list.add(dataMap);
            });
        }
        pager.setTotalCount(page.getTotalElements());
        pager.setData(list);
        return pager;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void verifyComment(CommentStatus status, Integer... commentIds) {
        if (commentIds != null) {
            for (int id : commentIds) {
                Comment comment = get(id);
                Assert.notNull(comment, "评论信息不存在!");
                commentDao.editCommentStatus(id, status.getId());
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void recommendComment(int commentId, boolean recommend) {
        Comment comment = get(commentId);
        Assert.notNull(comment, "评论信息不存在!");
        Assert.isTrue(comment.getStatus() == CommentStatus.PASSED.getId(), comment.getStatusName() + "状态的评论不能推荐!");
        commentDao.editCommentRecommend(commentId, recommend);

        getCache().evict(commentId);
    }

    /**
     * 评论或回复 只需要包装内容,所属父类ID,所属文章ID,用户名即可
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/2 13:12
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(Comment comment) {
        //管理员回复或评论
        if (StringUtils.hasLength(comment.getUserName())) {
            comment.setStatus(CommentStatus.PASSED.getId());
        } else if (StringUtils.hasLength(comment.getMemberUserName())) {
            comment.setStatus(CommentStatus.WAIT_CHECK.getId());
        }
        //评论回复
        if (comment.getParentId() > 0) {
            Comment parent = get(comment.getParentId());
            Assert.notNull(parent, "不存在的引用评论!");
            Assert.isTrue(parent.getStatus() != CommentStatus.WAIT_CHECK.getId(), "未审核的评论不允许回复!");
            Assert.isTrue(parent.getStatus() != CommentStatus.UNPASSED.getId(), "审核不通过的评论不允许回复!");
            Assert.isTrue(parent.getArticleId() == comment.getArticleId(), "评论所属内容ID错误!");
        }

        Article article = articleService.get(comment.getArticleId());
        Assert.notNull(article, "评论文章不存在!");
        Assert.isTrue(article.getStatus() == ArticleStatus.PASSED.getId(), article.getStatusName() + "状态的文章不允许评论!");

        comment.setPostDate(new Date());
        comment.setRecommend(false);
        comment.setDowns(0);
        comment.setUps(0);

        CommentTxt txt = comment.getCommentTxt();
        Assert.notNull(txt, "评论内容不能为空!");
        Assert.notNull(txt.getText(), "评论内容不能为空!");
        txt.setIp(IpThreadLocal.get());
        IPLocation location = IpSeekerUtil.getIPLocation(txt.getIp());
        if (location != null) {
            txt.setArea(location.getCountry() + " " + location.getArea());
        }
        comment.setCommentTxt(null);
        super.save(comment);

        txt.setCommentId(comment.getId());
        commentTxtDao.save(txt);
        articleService.incrementArticleComments(article.getId(), 1);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int remove(Integer... integers) {
        int count = 0;
        if (integers != null) {
            Comment comment;
            for (int id : integers) {
                comment = get(id);
                if (comment != null) {
                    // 删除对应文章的评论数
                    articleService.incrementArticleComments(comment.getArticleId(), -1);
                    commentDao.delete(comment);
                    ++count;
                }
                getCache().evict(id);
            }
        }
        return count;
    }

    @Override
    public Map<String, BigInteger> getTodayAndTotalComment() {
        return commentDao.getTodayAndTotalComment();
    }

    @Override
    public List<Map<String, Object>> getLatestComment(int limit, Integer status) {
        List<Map<String, Object>> list = commentDao.getLatestComment(limit, status);
        if (list != null) {
            String statusStr = "status", face = "face", articleId = "articleId";
            for (int i = 0; i < list.size(); i++) {
                Map<String, Object> map = list.get(i);
                Map<String, Object> m = new HashMap<>(map.size());
                m.putAll(map);
                if (m.containsKey(statusStr)) {
                    CommentStatus commentStatus = CommentStatus.getStatus((Integer) m.get(statusStr));
                    m.put("statusName", commentStatus == null ? "" : commentStatus.getName());
                }
                if (m.containsKey(articleId)) {
                    Integer aId = (Integer) m.get(articleId);
                    if (aId != null) {
                        m.put("articleIdStr", IDEncrypt.encode(aId.intValue()));
                    }
                }
                list.set(i, m);
            }
        }
        return list;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upDownComment(int commentId, boolean isUp) {
        Member member = MemberThreadLocal.get();
        String key = member.getId() + "-" + commentId;
        if (EhCacheUtil.isExist(EhCacheUtil.COMMENT_UPDOWN_CACHE, key)) {
            //在缓存时间内再次操作无效
            return;
        }
        EhCacheUtil.setData(EhCacheUtil.COMMENT_UPDOWN_CACHE, key, 1);
        if (isUp) {
            commentDao.upComment(commentId);
        } else {
            commentDao.downComment(commentId);
        }
        getCache().evict(commentId);
    }
}
