package com.bfly.cms.enums;

/**
 * 文章状态
 *  发布中状态是已经审核通过后 但发布日期尚未到--系统会自动发布文章
 * @author andy_hulibo@163.com
 * @date 2019/8/1 13:46
 */
public enum ArticleStatus {

    DRAFT(0, "草稿"),
    WAIT_CHECK(1, "待审核"), PASSED(2, "审核通过"),
    UNPASSED(3, "审核未通过"),

    /**
     * 发布中状态是已经审核通过后 但发布日期尚未到--系统会自动发布文章
     */
    PASSING(5,"发布中");

    private int id;
    private String name;

    ArticleStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * 获得内容状态
     *
     * @author andy_hulibo@163.com
     * @date 2019/8/1 11:17
     */
    public static ArticleStatus getStatus(int id) {
        for (ArticleStatus status : ArticleStatus.values()) {
            if (status.getId() == id) {
                return status;
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
