package com.bfly.cms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * CMS内容文本
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 13:39
 */
@Entity
@Table(name = "article_txt")
public class ArticleTxt implements Serializable {


    private static final long serialVersionUID = 1285660022162142857L;

    @Id
    @Column(name = "article_id", unique = true, nullable = false)
    private int articleId;

    /**
     * 文章内容
     */
    @Column(name = "txt")
    private String txt;


    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

}