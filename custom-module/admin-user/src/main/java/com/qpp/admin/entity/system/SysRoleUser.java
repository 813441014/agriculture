package com.qpp.admin.entity.system;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class SysRoleUser implements Serializable {
    private String userId;

    private String roleId;

    private static final long serialVersionUID = 1L;
}