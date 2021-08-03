package com.bfly.cms.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 城市
 * @author andy_hulibo@163.com
 * @date 2020/8/26 11:11
 */
@Entity
@Table(name = "city")
public class City implements Serializable {
    private static final long serialVersionUID = -5593353921647264095L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 省市区名称
     * @author andy_hulibo@163.com
     * @date 2020/8/26 11:11
     */
    @Column(name = "name")
    private String name;

    @Column(name = "parent_id")
    private int parentId;

    /**
     * 简称
     * @author andy_hulibo@163.com
     * @date 2020/8/26 11:12
     */
    @Column(name = "short_name")
    private String shortName;

    /**
     * 级别:0,中国；1，省份；2，市；3，区、县
     * @author andy_hulibo@163.com
     * @date 2020/8/26 11:12
     */
    @Column(name = "level")
    private int level;

    /**
     * 城市代码
     * @author andy_hulibo@163.com
     * @date 2020/8/26 11:13
     */
    @Column(name = "city_code")
    private String cityCode;
    
    /**
     * 邮编
     * @author andy_hulibo@163.com
     * @date 2020/8/26 11:13
     */
    @Column(name = "zip_code")
    private String zipCode;

    /**
     * 经度
     * @author andy_hulibo@163.com
     * @date 2020/8/26 11:14
     */
    @Column(name = "lng")
    private String lng;

    /**
     * 维度
     * @author andy_hulibo@163.com
     * @date 2020/8/26 11:14
     */
    @Column(name = "lat")
    private String lat;
    
    /**
     * 拼音
     * @author andy_hulibo@163.com
     * @date 2020/8/26 11:14
     */
    @Column(name = "pinyin")
    private String pinyin;

    /**
     * 状态
     * @author andy_hulibo@163.com
     * @date 2020/8/26 11:15
     */
    @Column(name = "status")
    private int status;

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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
