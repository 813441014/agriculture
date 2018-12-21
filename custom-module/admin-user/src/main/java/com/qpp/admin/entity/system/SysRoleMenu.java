package com.qpp.admin.entity.system;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class SysRoleMenu implements Serializable {
    private String roleId;

    private String menuId;

    private static final long serialVersionUID = 1L;
}