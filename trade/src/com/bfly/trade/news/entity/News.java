package com.bfly.trade.news.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 新闻资讯类
 * @author 胡礼波 2012-4-24 下午09:52:00
 */
public class News implements Serializable {

	/**
	 * @author 胡礼波 2012-4-24 下午09:51:57
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	/**
	 * 咨询类型
	 */
	private int typeId;
	
	/**
	 * 标题
	 */
	private String title;
	
	/**
	 * 外部连接
	 */
	private String outUrl;
	
	/**
	 * 作者
	 */
	private String author;
	
	/**
	 * 来源
	 */
	private String origin;
	
	/**
	 * 来源地址
	 */
	private String originUrl;
	
	/**
	 * 发布时间
	 */
	private Date publishTime;
	
	/**
	 * 是否推荐
	 */
	private boolean recommend;
	
	/**
	 * 状态
	 */
	private int status;
	
	/**
	 * 点击率
	 */
	private int clickCount;
	
	/**
	 * 标题略缩图
	 */
	private String titleImg;
	
	/**
	 * 内容
	 */
	private String content;
	
	/**
	 * 是否允许评论
	 */
	private boolean allowComment;

	/**
	 * 文章标签
	 */
	private String tags;
	
	/**
	 * SEO关键字
	 */
	private String keyWords;
	
	/**
	 * SEO咨询描述
	 */
	private String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOutUrl() {
		return outUrl;
	}

	public void setOutUrl(String outUrl) {
		this.outUrl = outUrl;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getOriginUrl() {
		return originUrl;
	}

	public void setOriginUrl(String originUrl) {
		this.originUrl = originUrl;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
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

	public int getClickCount() {
		return clickCount;
	}

	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}

	public String getTitleImg() {
		return titleImg;
	}

	public void setTitleImg(String titleImg) {
		this.titleImg = titleImg;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isAllowComment() {
		return allowComment;
	}

	public void setAllowComment(boolean allowComment) {
		this.allowComment = allowComment;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
