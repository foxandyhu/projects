package com.bfly.cms.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 职位申请表
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:29
 */
@Entity
@Table(name = "job_apply")
public class JobApply implements Serializable {

    private static final long serialVersionUID = 2166370538443637214L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 申请标题
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/28 15:34
     */
    @Column(name = "title")
    private String title;

    /**
     * 申请时间
     */
    @Column(name = "apply_time")
    private Date applyTime;

    /**
     * 所属文章
     */
    @Column(name = "article_id")
    private int articleId;

    /**
     * 所属用户
     */
    @ManyToOne
    @JoinColumn(name = "member_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private Member member;


    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}