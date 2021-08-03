package com.bfly.cms.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 评分组
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/17 9:45
 */
@Entity
@Table(name = "score_group")
public class ScoreGroup implements Serializable {

    private static final long serialVersionUID = 5630185372905096874L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 分组名
     */
    @Column(name = "name")
    @NotBlank(message = "分组名称不能为空!")
    private String name;

    /**
     * 描述
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 评分项
     */
    @OneToMany(mappedBy = "group", fetch = FetchType.EAGER)
    private List<ScoreItem> items;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<ScoreItem> getItems() {
        return items;
    }

    public void setItems(List<ScoreItem> items) {
        this.items = items;
    }
}