package com.bfly.cms.entity;

import com.bfly.cms.enums.GuestBookStatus;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 留言
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 17:18
 */
@Entity
@Table(name = "guestbook")
public class GuestBook implements Serializable {

    /**
     * 数据字典类型名称
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/2 16:54
     */
    public static final String GUESTBOOK_TYPE_DIR = "guestbook_type";
    private static final long serialVersionUID = -4237675121045605032L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 留言时间
     */
    @Column(name = "post_date")
    private Date postDate;

    /**
     * 状态
     *
     * @see GuestBookStatus
     */
    @Column(name = "status")
    private int status;

    /**
     * 是否推荐
     */
    @Column(name = "is_recommend")
    private boolean recommend;

    /**
     * 留言扩展信息
     */
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    @NotFound(action = NotFoundAction.IGNORE)
    private GuestBookExt ext;

    /**
     * 回复留言用户名
     */
    @Column(name = "reply_user_name")
    private String replyUserName;

    /**
     * 留言的会员用户名
     */
    @Column(name = "post_user_name")
    private String postUserName;

    @ManyToOne
    @JoinColumn(name = "post_user_name", insertable = false, updatable = false, referencedColumnName = "user_name")
    @NotFound(action = NotFoundAction.IGNORE)
    private Member member;

    /**
     * 留言所属类型
     *
     * @see com.bfly.cms.enums.GuestBookTypeEnum
     */
    @Column(name = "type_id")
    private int type;

    /**
     * 标识该条留言是否回复
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/2 15:49
     */
    @Column(name = "is_reply")
    private boolean reply;

    /**
     * 回复时间
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/2 15:52
     */
    @Column(name = "reply_date")
    private Date replyDate;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getStatusName() {
        GuestBookStatus status = GuestBookStatus.getStatus(getStatus());
        return status == null ? "" : status.getName();
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public boolean isReply() {
        return reply;
    }

    public void setReply(boolean reply) {
        this.reply = reply;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
    }

    public GuestBookExt getExt() {
        return ext;
    }

    public void setExt(GuestBookExt ext) {
        this.ext = ext;
    }

    public String getReplyUserName() {
        return replyUserName;
    }

    public void setReplyUserName(String replyUserName) {
        this.replyUserName = replyUserName;
    }

    public String getPostUserName() {
        return postUserName;
    }

    public void setPostUserName(String postUserName) {
        this.postUserName = postUserName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}