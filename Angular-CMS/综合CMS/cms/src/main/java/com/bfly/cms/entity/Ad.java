package com.bfly.cms.entity;

import com.bfly.cms.enums.AdStatusEnum;
import com.bfly.cms.enums.AdTypeEnum;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * CMS广告
 *
 * @author andy_hulibo@163.com
 * @date 2018/11/16 16:42
 */
@Entity
@Table(name = "ad")
public class Ad implements Serializable {

    private static final long serialVersionUID = 71159170989050961L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * 广告名称
     */
    @Column(name = "name")
    @NotBlank(message = "广告名称不能为空!")
    private String name;

    /**
     * 广告类型
     *
     * @see AdTypeEnum
     */
    @Column(name = "type")
    private int type;

    /**
     * 展现次数
     */
    @Column(name = "display_count")
    @Min(value = 0, message = "展现次数必须是正整数0!")
    private int displayCount;

    /**
     * 点击次数
     */
    @Column(name = "click_count")
    @Min(value = 0, message = "点击次数必须是正整数0!")
    private int clickCount;

    /**
     * 开始时间
     */
    @Column(name = "start_time")
    private Date startTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 是否启用
     */
    @Column(name = "is_enabled")
    private boolean enabled;

    /**
     * 状态
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/25 14:26
     */
    @Column(name = "status")
    private int status;

    /**
     * 所属广告位
     */
    @Column(name = "space_id")
    private int spaceId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "space_id", referencedColumnName = "id", updatable = false, insertable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private AdSpace space;

    /**
     * 广告内容属性
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/8 16:36
     */
    @OneToOne(cascade = {CascadeType.ALL})
    @PrimaryKeyJoinColumn(referencedColumnName = "ad_id")
    @NotFound(action = NotFoundAction.IGNORE)
    private AdTxt txt;

    /**
     * 获得类型名称
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/18 10:40
     */
    public String getTypeName() {
        AdTypeEnum type = AdTypeEnum.getType(getType());
        return type == null ? "" : type.getName();
    }

    /**
     * 点击率
     *
     * @author andy_hulibo@163.com
     * @date 2019/7/18 11:25
     */
    public String getClickRate() {
        return (getDisplayCount() == 0 ? 0 : (getClickCount() / getDisplayCount())) + "%";
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 状态名称
     *
     * @author andy_hulibo@163.com
     * @date 2020/6/25 14:29
     */
    public String getStatusName() {
        AdStatusEnum statusEnum = AdStatusEnum.getStatus(getStatus());
        return statusEnum == null ? "" : statusEnum.getName();
    }

    public AdTxt getTxt() {
        return txt;
    }

    public void setTxt(AdTxt txt) {
        this.txt = txt;
    }

    public AdSpace getSpace() {
        return space;
    }

    public void setSpace(AdSpace space) {
        this.space = space;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getDisplayCount() {
        return displayCount;
    }

    public void setDisplayCount(int displayCount) {
        this.displayCount = displayCount;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(int spaceId) {
        this.spaceId = spaceId;
    }
}