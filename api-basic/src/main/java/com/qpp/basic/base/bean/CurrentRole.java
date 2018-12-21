package com.qpp.basic.base.bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author langmingsheng
 */
@Getter
@Setter
@ToString
public class CurrentRole {

    private String id;

    private String roleName;

    private String remark;

    public CurrentRole(String id, String roleName, String remark) {
        this.id = id;
        this.roleName = roleName;
        this.remark = remark;
    }
}
