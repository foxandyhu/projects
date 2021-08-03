package com.bfly.cms.entity;

import com.bfly.core.config.ResourceConfigure;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 评分项
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 9:45
 */
@Entity
@Table(name = "score_item")
public class ScoreItem implements Serializable, Comparable<ScoreItem> {

    private static final long serialVersionUID = -4901096609694109527L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 评分名
     */
    @Column(name = "name")
    @NotBlank(message = "评分名称不能为空!")
    private String name;

    /**
     * 分值
     */
    @Column(name = "score")
    @Min(value = 0, message = "评分值必须大于0!")
    private int score;

    /**
     * 评分图标路径
     */
    @Column(name = "pic")
    private String pic;

    /**
     * 排序
     */
    @Column(name = "seq")
    @Min(value = 0, message = "评分顺序必须大于0!")
    private int seq;

    /**
     * 评分组
     */
    @ManyToOne
    @JoinColumn(name = "group_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private ScoreGroup group;

    @Override
    public int compareTo(ScoreItem o) {
        return this.getSeq() - o.getSeq();
    }

    public String getUrl() {
        return StringUtils.isNotBlank(getPic()) ? ResourceConfigure.getResourceHttpUrl(getPic()) : "";
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public ScoreGroup getGroup() {
        return group;
    }

    public void setGroup(ScoreGroup group) {
        this.group = group;
    }
}