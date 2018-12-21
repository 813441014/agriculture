package com.qpp.admin.entity.system;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class SysUser implements Serializable {
    private String id;

    private String username;

    private String password;

    private Integer age;

    private String email;

    private String photo;

    private String realName;

    private String createBy;

    private String updateBy;

    private Date createDate;

    private Date updateDate;

    private Byte delFlag;

    private static final long serialVersionUID = 1L;
}