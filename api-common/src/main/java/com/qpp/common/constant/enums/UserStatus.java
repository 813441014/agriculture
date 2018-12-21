package com.qpp.common.constant.enums;


/**
 * @ClassName UserStatus
 * @Description TODO 用户状态
 * @Author qipengpai
 * @Date 2018/10/23 10:27
 * @Version 1.0.1
 */
public enum UserStatus {
    OK("0", "正常"), DISABLE("1", "停用"), DELETED("2", "删除");

    private final String code;
    private final String info;

    UserStatus(String code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public String getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
