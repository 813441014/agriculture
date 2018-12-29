package com.qpp.common.constant.enums;

import java.util.function.Supplier;

/**
 * @ClassName BusinessStatus
 * @Description TODO 数据源枚举
 * @Author qipengpai
 * @Date 2018/10/23 11:31
 * @Version 1.0.1
 */
public enum DataSourceType implements Supplier<DataSourceType> {

    /**
     * 系统管理端
     */
    ADMIN,

    /**
     * 客户端用户
     */
    USER,

    /**
     * 日志
     */
    LOG,

    /**
     * 文件数据源
     */
    FILE,

    /**
     * 文件更多数据源
     */
    FILE_HQ;

    //初始为系统管理端
    @Override
    public DataSourceType get() {
        return ADMIN;
    }
}
