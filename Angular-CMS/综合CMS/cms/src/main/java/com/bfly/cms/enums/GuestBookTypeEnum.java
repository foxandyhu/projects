package com.bfly.cms.enums;

/**
 * 留言类型
 * @author andy_hulibo@163.com
 * @date 2020/6/21 15:55
 */
public enum GuestBookTypeEnum {

    PIC(1, "投诉"), TEXT(2, "建议");

    private int id;
    private String name;

    GuestBookTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 留言类型
     * @author andy_hulibo@163.com
     * @date 2019/7/18 10:39
     */
    public static GuestBookTypeEnum getType(int id) {
        for (GuestBookTypeEnum type : GuestBookTypeEnum.values()) {
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
