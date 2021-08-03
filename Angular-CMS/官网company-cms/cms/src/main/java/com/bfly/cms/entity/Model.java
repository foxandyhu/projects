package com.bfly.cms.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * CMS模型类
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 11:29
 */
@Entity
@Table(name = "model")
public class Model implements Serializable {

    private static final long serialVersionUID = 8862206214016015186L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 名称
     */
    @Column(name = "name")
    @NotBlank(message = "模型名称不能为空!")
    private String name;


    /**
     * 排列顺序
     */
    @Column(name = "seq")
    private int seq;

    /**
     * 是否有内容
     * false 标识没有内容模型 true表示有内容模型
     */
    @Column(name = "has_content")
    private boolean hasContent;

    /**
     * 是否禁用
     */
    @Column(name = "is_enabled")
    private boolean enabled;

    /**
     * 描述
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/5 11:53
     */
    @Column(name = "remark")
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public boolean isHasContent() {
        return hasContent;
    }

    public void setHasContent(boolean hasContent) {
        this.hasContent = hasContent;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}