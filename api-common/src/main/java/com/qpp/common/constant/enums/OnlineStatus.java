package com.qpp.common.constant.enums;

/**
 * @author qipengpai
 * @Title: OnlineStatus
 * @ProjectName misscy
 * @Description: TODO 用户在线枚举
 * @date 10:30 2018/10/22
 */
public enum OnlineStatus {

    /** 用户状态 */
    ON_LINE("在线"),OFF_LINE("离线");

    private final String info;

    OnlineStatus(String info) {
        this.info = info;
    }

    public String getInfo(){
        return info;
    }
}
