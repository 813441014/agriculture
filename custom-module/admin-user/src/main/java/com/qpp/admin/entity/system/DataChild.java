package com.qpp.admin.entity.system;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class DataChild implements Serializable {
    /**
     * 数据字典子类ID
     */
    private Long id;

    /**
     * 父类id
     */
    private Integer parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String describe;

    /**
     * 标识
     */
    private String identify;

    /**
     * 状态：1.启用，2.禁用
     */
    private Integer status;

    /**
     * 预留字段1
     */
    private String reserved1;

    /**
     * 预留字段2
     */
    private String reserved2;

    /**
     * 创建时间
     */
    private Date makeTime;

    /**
     * 获取数据字典子类ID
     *
     * @return id - 数据字典子类ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置数据字典子类ID
     *
     * @param id 数据字典子类ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取父类id
     *
     * @return parent_id - 父类id
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父类id
     *
     * @param parentId 父类id
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取描述
     *
     * @return describe - 描述
     */
    public String getDescribe() {
        return describe;
    }

    /**
     * 设置描述
     *
     * @param describe 描述
     */
    public void setDescribe(String describe) {
        this.describe = describe;
    }

    /**
     * 获取标识
     *
     * @return identify - 标识
     */
    public String getIdentify() {
        return identify;
    }

    /**
     * 设置标识
     *
     * @param identify 标识
     */
    public void setIdentify(String identify) {
        this.identify = identify;
    }

    /**
     * 获取状态：1.启用，2.禁用
     *
     * @return status - 状态：1.启用，2.禁用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态：1.启用，2.禁用
     *
     * @param status 状态：1.启用，2.禁用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取预留字段1
     *
     * @return reserved1 - 预留字段1
     */
    public String getReserved1() {
        return reserved1;
    }

    /**
     * 设置预留字段1
     *
     * @param reserved1 预留字段1
     */
    public void setReserved1(String reserved1) {
        this.reserved1 = reserved1;
    }

    /**
     * 获取预留字段2
     *
     * @return reserved2 - 预留字段2
     */
    public String getReserved2() {
        return reserved2;
    }

    /**
     * 设置预留字段2
     *
     * @param reserved2 预留字段2
     */
    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }

    /**
     * 获取创建时间
     *
     * @return make_time - 创建时间
     */
    public Date getMakeTime() {
        return makeTime;
    }

    /**
     * 设置创建时间
     *
     * @param makeTime 创建时间
     */
    public void setMakeTime(Date makeTime) {
        this.makeTime = makeTime;
    }
}