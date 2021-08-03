package com.bfly.cms.entity;

import com.bfly.core.config.ResourceConfigure;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 广告内容
 *
 * @author andy_hulibo@163.com
 * @date 2020/6/8 16:35
 */
@Entity
@Table(name = "ad_txt")
public class AdTxt implements Serializable {

    private static final long serialVersionUID = 3334253217365074658L;

    @Id
    @Column(name = "ad_id", unique = true, nullable = false)
    private int adId;

    /**
     * 图片地址
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/8 16:38
     */
    @Column(name = "pic_path")
    private String picPath;

    /**
     * 连接或图片提示
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/8 16:39
     */
    @Column(name = "pic_tip")
    private String picTip;

    /**
     * 图片宽度
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/8 16:39
     */
    @Column(name = "pic_width")
    private int picWidth;

    /**
     * 图片高度
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/8 16:40
     */
    @Column(name = "pic_height")
    private int picHeight;

    /**
     * 打开方式
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/8 16:40
     */
    @Column(name = "link_target")
    private String linkTarget;

    /**
     * 文字内容
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/8 16:41
     */
    @Column(name = "txt_content")
    private String txtContent;

    /**
     * 文字颜色
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/8 16:41
     */
    @Column(name = "txt_color")
    private String txtColor;

    /**
     * 文字连接
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/8 16:41
     */
    @Column(name = "link")
    private String link;

    /**
     * 文字大小
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/8 16:42
     */
    @Column(name = "txt_size")
    private String txtSize;

    /**
     * 可执行代码
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/8 16:42
     */
    @Column(name = "code_content")
    private String codeContent;

    public String getUrl() {
        return StringUtils.isNotBlank(getPicPath()) ? ResourceConfigure.getResourceHttpUrl(getPicPath()) : "";
    }

    public int getAdId() {
        return adId;
    }

    public void setAdId(int adId) {
        this.adId = adId;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    public String getPicTip() {
        return picTip;
    }

    public void setPicTip(String picTip) {
        this.picTip = picTip;
    }

    public int getPicWidth() {
        return picWidth;
    }

    public void setPicWidth(int picWidth) {
        this.picWidth = picWidth;
    }

    public int getPicHeight() {
        return picHeight;
    }

    public void setPicHeight(int picHeight) {
        this.picHeight = picHeight;
    }

    public String getLinkTarget() {
        return linkTarget;
    }

    public void setLinkTarget(String linkTarget) {
        this.linkTarget = linkTarget;
    }

    public String getTxtContent() {
        return txtContent;
    }

    public void setTxtContent(String txtContent) {
        this.txtContent = txtContent;
    }

    public String getTxtColor() {
        return txtColor;
    }

    public void setTxtColor(String txtColor) {
        this.txtColor = txtColor;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTxtSize() {
        return txtSize;
    }

    public void setTxtSize(String txtSize) {
        this.txtSize = txtSize;
    }

    public String getCodeContent() {
        return codeContent;
    }

    public void setCodeContent(String codeContent) {
        this.codeContent = codeContent;
    }
}
