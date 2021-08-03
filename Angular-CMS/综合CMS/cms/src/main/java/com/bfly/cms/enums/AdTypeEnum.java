package com.bfly.cms.enums;

/**
 * 广告类型
 * @author andy_hulibo@163.com
 * @date 2019/7/18 10:38
 */
public enum AdTypeEnum {

    PIC(1, "图片"), TEXT(2, "文字"), CODE(3, "代码");

    private int id;
    private String name;

    AdTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 获得广告类型
     * @author andy_hulibo@163.com
     * @date 2019/7/18 10:39
     */
    public static AdTypeEnum getType(int id) {
        for (AdTypeEnum type : AdTypeEnum.values()) {
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
