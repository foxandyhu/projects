package com.bfly.cms.entity;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 登录配置类
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/21 12:14
 */
@Entity
@Table(name = "member_login_config")
public class LoginConfig implements Serializable {

    private static final long serialVersionUID = -3351712723103191847L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 是否开启会员功能
     */
    @Column(name = "is_open_login")
    private boolean openLogin;

    /**
     * 登录错误次数,达到错误次数则显示验证码
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/21 12:21
     */
    @Column(name = "login_error")
    private int loginError;

    /**
     * 登录错误超时时间,超过时间重计次数 单位分钟
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/21 12:22
     */
    @Column(name = "login_error_timeout")
    private int loginErrorTimeOut;

    /**
     * @author andy_hulibo@163.com
     * @date 2019/7/21 12:23
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "login_email_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private EmailProvider emailProvider;

    /**
     * 找回密码标题
     */
    @Column(name = "retrieve_pwd_title")
    private String retrievePwdTitle;

    /**
     * 找回密码内容
     */
    @Column(name = "retrieve_pwd_text")
    private String retrievePwdText;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isOpenLogin() {
        return openLogin;
    }

    public void setOpenLogin(boolean openLogin) {
        this.openLogin = openLogin;
    }

    public String getRetrievePwdTitle() {
        return retrievePwdTitle;
    }

    public void setRetrievePwdTitle(String retrievePwdTitle) {
        this.retrievePwdTitle = retrievePwdTitle;
    }

    public String getRetrievePwdText() {
        return retrievePwdText;
    }

    public void setRetrievePwdText(String retrievePwdText) {
        this.retrievePwdText = retrievePwdText;
    }

    public int getLoginError() {
        return loginError;
    }

    public void setLoginError(int loginError) {
        this.loginError = loginError;
    }

    public int getLoginErrorTimeOut() {
        return loginErrorTimeOut;
    }

    public void setLoginErrorTimeOut(int loginErrorTimeOut) {
        this.loginErrorTimeOut = loginErrorTimeOut;
    }

    public EmailProvider getEmailProvider() {
        return emailProvider;
    }

    public void setEmailProvider(EmailProvider emailProvider) {
        this.emailProvider = emailProvider;
    }
}