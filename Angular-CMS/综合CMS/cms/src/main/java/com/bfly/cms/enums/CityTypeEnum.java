package com.bfly.cms.enums;

/**
 * 城市类型
 * @author andy_hulibo@163.com
 * @date 2020/8/26 11:27
 */
public enum CityTypeEnum {

    COUNTRY(0, "国家"), PROVINCE(1, "省"), CITY(2, "市"),AREA(3,"区、县");

    private int id;
    private String name;

    CityTypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 城市类型
     * @author andy_hulibo@163.com
     * @date 2019/7/18 10:39
     */
    public static CityTypeEnum getType(int id) {
        for (CityTypeEnum type : CityTypeEnum.values()) {
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
