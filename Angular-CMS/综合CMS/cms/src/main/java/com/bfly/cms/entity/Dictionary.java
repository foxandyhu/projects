package com.bfly.cms.entity;

import com.bfly.core.config.ResourceConfigure;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 数据字典
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 16:18
 */
@Entity
@Table(name = "d_dictionary")
public class Dictionary implements Serializable {

    private static final long serialVersionUID = -5870692912576937801L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotBlank(message = "数据名称不能为空!")
    private String name;

    @Column(name = "value")
    private String value;

    @Column(name = "type")
    @NotBlank(message = "数据分类不能为空!")
    private String type;

    /**
     * 分类图标
     *
     * @author andy_hulibo@163.com
     * @date 2020/5/26 10:19
     */
    @Column(name = "icon")
    private String icon;

    /**
     * 描述
     *
     * @author andy_hulibo@163.com
     * @date 2020/3/27 21:42
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 是否启用
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/7 16:26
     */
    @Column(name = "is_enabled")
    private boolean enabled;

    /**
     * 排序 值越小越靠前
     * @author andy_hulibo@163.com
     * @date 2020/6/13 15:30
     */
    @Column(name = "seq")
    private int seq;

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getValue() {
        return value;
    }


    public void setValue(String value) {
        this.value = value;
    }


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }

    /**
     * 图标完整路径
     *
     * @author andy_hulibo@163.com
     * @date 2020/5/26 10:22
     */
    public String getUrl() {
        return StringUtils.isNotBlank(getIcon()) ? ResourceConfigure.getResourceHttpUrl(getIcon()) : "";
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}