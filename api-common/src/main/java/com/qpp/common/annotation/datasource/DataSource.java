package com.qpp.common.annotation.datasource;

import com.qpp.common.constant.enums.DataSourceType;

import java.lang.annotation.*;

/**
 * @author qipengpai
 * @Title: DataSource
 * @ProjectName misscy
 * @Description: TODO 数据源注解
 * @date 10:30 2018/10/22
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSource {
    /**
     * 切换数据源名称
     * @return
     */
    DataSourceType value() default DataSourceType.ADMIN;
}
