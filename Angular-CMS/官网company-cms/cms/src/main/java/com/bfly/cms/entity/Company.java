package com.bfly.cms.entity;

import com.bfly.core.config.ResourceConfigure;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公司信息
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/15 17:45
 */
@Entity
@Table(name = "d_company")
public class Company implements Serializable {

    private static final long serialVersionUID = 8689011643274322584L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 公司名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 公司规模
     */
    @Column(name = "scale")
    private String scale;

    /**
     * 公司性质
     */
    @Column(name = "nature")
    private String nature;

    /**
     * 公司行业
     */
    @Column(name = "industry")
    private String industry;

    /**
     * 联系电话
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 企业邮箱
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 10:57
     */
    @Column(name = "email")
    private String email;

    /**
     * 公司简介
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 公司地址
     */
    @Column(name = "address")
    private String address;

    /**
     * 微信
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/22 11:05
     */
    @Column(name = "weixin")
    private String weixin;

    /**
     * qq号--多个用逗号隔开
     * 格式为 名称1:QQ号1,名称2:QQ号2,名称3:QQ号3,
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/28 13:23
     */
    @Column(name = "qq")
    private String qq;

    /**
     * QQ号转变为集合
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/28 13:25
     */
    public List<Map<String, String>> getQqs() {
        List<Map<String, String>> list = new ArrayList<>();
        if (StringUtils.isNotBlank(getQq())) {
            String qqs[] = getQq().split(",");
            for (String qs : qqs) {
                String qq[] = qs.split(":");
                if (qq.length == 2) {
                    Map<String, String> map = new HashMap<>();
                    map.put(qq[0], qq[1]);
                    list.add(map);
                }
            }
        }
        return list;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWeiXinUrl() {
        return StringUtils.isNotBlank(getWeixin()) ? ResourceConfigure.getResourceHttpUrl(getWeixin()) : "";
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

    public String getScale() {
        return scale;
    }

    public void setScale(String scale) {
        this.scale = scale;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }
}