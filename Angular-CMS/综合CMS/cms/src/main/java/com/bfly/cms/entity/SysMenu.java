package com.bfly.cms.entity;

import com.alibaba.fastjson.annotation.JSONField;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

/**
 * 系统菜单
 *
 * @author andy_hulibo@163.com
 * @date 2019/7/12 18:30
 */
@Entity
@Table(name = "sys_menu")
public class SysMenu implements Serializable {

    private static final long serialVersionUID = 9161811857617910658L;

    public SysMenu() {
    }

    public SysMenu(String url) {
        this.url = url;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 菜单名称
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/12 18:35
     */
    @Column(name = "name")
    @NotBlank(message = "菜单名称不能为空!")
    private String name;

    /**
     * 菜单链接地址
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/12 18:35
     */
    @NotBlank(message = "链接地址不能为空!")
    @Column(name = "url")
    private String url;

    /**
     * 菜单排序
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/12 18:36
     */
    @Column(name = "seq")
    private int seq;

    /**
     * 父节点ID
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/29 19:38
     */
    @Column(name = "parent_id")
    private int parentId;

    /**
     * 父节点
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/12 18:44
     */
    @ManyToOne
    @JoinColumn(name = "parent_id", updatable = false, insertable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    @JSONField(serialize = false)
    private SysMenu parent;

    /**
     * 子节点
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/12 18:44
     */
    @OneToMany(mappedBy = "parent", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @OrderBy("seq")
    private List<SysMenu> children;

    /**
     * 角色菜单关系维护
     */
    @ManyToMany(mappedBy = "menus",fetch = FetchType.EAGER)
    @JSONField(serialize = false)
    private List<UserRole> roles;

    /**
     * 是否是操作级别的菜单
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/29 23:19
     */
    @Column(name = "is_action")
    private boolean action;

    /**
     * 图标
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/29 23:30
     */
    @Column(name = "icon")
    private String icon;

    /**
     * 前端Angular控件只识别title
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/30 15:22
     */
    public String getTitle() {
        return getName();
    }

    /**
     * 前端Angular控件只识别link
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/30 16:09
     */
    public String getLink() {
        return getUrl();
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isAction() {
        return action;
    }

    public void setAction(boolean action) {
        this.action = action;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public List<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRole> roles) {
        this.roles = roles;
    }

    public SysMenu getParent() {
        return parent;
    }

    public void setParent(SysMenu parent) {
        this.parent = parent;
    }

    public List<SysMenu> getChildren() {
        return children;
    }

    public void setChildren(List<SysMenu> children) {
        this.children = children;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }
}
