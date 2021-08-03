package com.bfly.cms.entity;

import com.bfly.cms.enums.ArticleStatus;
import com.bfly.cms.enums.ContentType;
import com.bfly.common.IDEncrypt;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * CMS 文章内容
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 12:02
 */
@Entity
@Table(name = "article")
public class Article implements Serializable {

    private static final long serialVersionUID = -5818575667316876719L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    /**
     * 置顶级别---级别越高越靠前
     */
    @Column(name = "top_level")
    private int topLevel;

    /**
     * 置顶失效期--指定日期到期自动失效 不指定长期置顶
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/7 16:31
     */
    @Column(name = "top_expired")
    private Date topExpired;

    /**
     * 是否推荐
     */
    @Column(name = "is_recommend")
    private boolean recommend;

    /**
     * 状态
     *
     * @see ArticleStatus
     */
    @Column(name = "status")
    private int status;

    /**
     * 总访问数
     */
    @Column(name = "views")
    private int views;

    /**
     * 总评论数
     */
    @Column(name = "comments")
    private int comments;


    /**
     * 总下载数
     */
    @Column(name = "downloads")
    private int downloads;


    /**
     * 总顶数
     */
    @Column(name = "ups")
    private int ups;

    /**
     * 总踩数
     */
    @Column(name = "downs")
    private int downs;

    /**
     * 评分数
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/7 16:20
     */
    @Column(name = "scores")
    private float scores;

    /**
     * 文章内容类型
     *
     * @see ContentType
     */
    @Column(name = "type_id")
    private int type;

    /**
     * 模型ID
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/19 23:31
     */
    @Column(name = "model_id")
    private int modelId;

    @ManyToOne
    @JoinColumn(name = "model_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Model model;

    /**
     * 文章内容所属频道
     */
    @Column(name = "channel_id")
    private int channelId;

    @ManyToOne
    @JoinColumn(name = "channel_id", insertable = false, updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Channel channel;

    /**
     * 是否运行分享
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/10 9:02
     */
    @Column(name = "is_share")
    private boolean share;

    /**
     * 是否运行评论
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/10 9:02
     */
    @Column(name = "is_comment")
    private boolean comment;

    /**
     * 是否允许顶踩
     */
    @Column(name = "is_updown")
    private boolean updown;

    /**
     * 是否评分
     */
    @Column(name = "is_score")
    private boolean score;

    /**
     * 评分组ID
     */
    @Column(name = "score_group_id")
    private int scoreGroupId;

    /**
     * 文章内容扩展信息
     */
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @NotFound(action = NotFoundAction.IGNORE)
    private ArticleExt articleExt;

    /**
     * 文章具体内容及扩展内容
     */
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @NotFound(action = NotFoundAction.IGNORE)
    private ArticleTxt articleTxt;

    /**
     * 文章内容发布者
     */
    private Integer userId;

    /**
     * 文章内容图片集
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "article_id")
    @OrderBy("seq")
    @Fetch(FetchMode.SUBSELECT)
    private List<ArticlePicture> pictures;


    /**
     * 文章内容附件
     */
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "article_id")
    private List<ArticleAttachment> attachments;

    /**
     * 文章内容扩展属性表
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "article_attr", joinColumns = @JoinColumn(name = "article_id"))
    @MapKeyColumn(name = "attr_name")
    @Column(name = "attr_value")
    private Map<String, String> attr;

    /**
     * 返回状态名称
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/1 13:51
     */
    public String getStatusName() {
        ArticleStatus status = ArticleStatus.getStatus(getStatus());
        return status == null ? "" : status.getName();
    }

    /**
     * 类型名称
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/18 17:01
     */
    public String getTypeName() {
        ContentType type = ContentType.getType(getType());
        return type != null ? type.getName() : "";
    }

    public int getModelId() {
        return modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public float getScores() {
        return scores;
    }

    public void setScores(float scores) {
        this.scores = scores;
    }

    public int getScoreGroupId() {
        return scoreGroupId;
    }

    public void setScoreGroupId(int scoreGroupId) {
        this.scoreGroupId = scoreGroupId;
    }

    public boolean isScore() {
        return score;
    }

    public void setScore(boolean score) {
        this.score = score;
    }

    public boolean isShare() {
        return share;
    }

    public void setShare(boolean share) {
        this.share = share;
    }

    public boolean isComment() {
        return comment;
    }

    public void setComment(boolean comment) {
        this.comment = comment;
    }

    public boolean isUpdown() {
        return updown;
    }

    public void setUpdown(boolean updown) {
        this.updown = updown;
    }

    public Date getTopExpired() {
        return topExpired;
    }

    public void setTopExpired(Date topExpired) {
        this.topExpired = topExpired;
    }

    /**
     * 加密后的ID字符串
     *
     * @author andy_hulibo@163.com
     * @date 2019/9/4 11:43
     */
    public String getIdStr() {
        return IDEncrypt.encode(getId());
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTopLevel() {
        return topLevel;
    }

    public void setTopLevel(int topLevel) {
        this.topLevel = topLevel;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public int getUps() {
        return ups;
    }

    public void setUps(int ups) {
        this.ups = ups;
    }

    public int getDowns() {
        return downs;
    }

    public void setDowns(int downs) {
        this.downs = downs;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public ArticleExt getArticleExt() {
        return articleExt;
    }

    public void setArticleExt(ArticleExt articleExt) {
        this.articleExt = articleExt;
    }

    public ArticleTxt getArticleTxt() {
        return articleTxt;
    }

    public void setArticleTxt(ArticleTxt articleTxt) {
        this.articleTxt = articleTxt;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<ArticlePicture> getPictures() {
        return pictures;
    }

    public void setPictures(List<ArticlePicture> pictures) {
        this.pictures = pictures;
    }

    public List<ArticleAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<ArticleAttachment> attachments) {
        this.attachments = attachments;
    }

    public Map<String, String> getAttr() {
        return attr;
    }

    public void setAttr(Map<String, String> attr) {
        this.attr = attr;
    }
}