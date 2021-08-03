package com.bfly.cms.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 注册配置类
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/21 12:17
 */
@Entity
@Table(name = "member_regist_config")
public class RegistConfig implements Serializable {

    private static final long serialVersionUID = -678795927954283134L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 是否开启注册功能
     */
    @Column(name = "is_open_registe")
    private boolean openRegiste;

    /**
     * 注册后是否需要审核
     */
    @Column(name = "is_need_check_registed")
    private boolean needCheckRegisted;

    /**
     * 会员注册标题
     */
    @Column(name = "registe_title")
    private String registeTitle;

    /**
     * 会员注册内容
     */
    @Column(name = "registe_text")
    private String registeText;

    /**
     * 注册邮件服务提供商
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/21 12:29
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "regist_email_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private EmailProvider emailProvider;

    /**
     * 保留的用户名 多个逗号隔开
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/21 12:33
     */
    @Column(name = "deny_user_name")
    private String denyUserName;

    /**
     * 用户名最小长度
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/21 12:34
     */
    @Column(name = "min_length_user_name")
    private int minLengthUserName;

    /**
     * 最小密码长度
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/21 12:35
     */
    @Column(name = "min_length_password")
    private int minLengthPassword;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOpenRegiste() {
        return openRegiste;
    }

    public void setOpenRegiste(boolean openRegiste) {
        this.openRegiste = openRegiste;
    }

    public boolean isNeedCheckRegisted() {
        return needCheckRegisted;
    }

    public void setNeedCheckRegisted(boolean needCheckRegisted) {
        this.needCheckRegisted = needCheckRegisted;
    }

    public String getRegisteTitle() {
        return registeTitle;
    }

    public void setRegisteTitle(String registeTitle) {
        this.registeTitle = registeTitle;
    }

    public String getRegisteText() {
        return registeText;
    }

    public void setRegisteText(String registeText) {
        this.registeText = registeText;
    }

    public EmailProvider getEmailProvider() {
        return emailProvider;
    }

    public void setEmailProvider(EmailProvider emailProvider) {
        this.emailProvider = emailProvider;
    }

    public String getDenyUserName() {
        return denyUserName;
    }

    public void setDenyUserName(String denyUserName) {
        this.denyUserName = denyUserName;
    }

    public int getMinLengthUserName() {
        return minLengthUserName;
    }

    public void setMinLengthUserName(int minLengthUserName) {
        this.minLengthUserName = minLengthUserName;
    }

    public int getMinLengthPassword() {
        return minLengthPassword;
    }

    public void setMinLengthPassword(int minLengthPassword) {
        this.minLengthPassword = minLengthPassword;
    }
}
