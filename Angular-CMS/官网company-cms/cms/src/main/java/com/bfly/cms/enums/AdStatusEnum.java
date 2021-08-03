package com.bfly.cms.enums;

/**
 * 广告类型
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/18 10:38
 */
public enum AdStatusEnum {

    UNPUBLISHED(1, "待发布"), PUBLISHING(2, "发布中"), PUBLISH_EXPIRED(3, "已过期");

    private int id;
    private String name;

    AdStatusEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 获得广告状态
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/18 10:39
     */
    public static AdStatusEnum getStatus(int id) {
        for (AdStatusEnum type : AdStatusEnum.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
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
}
