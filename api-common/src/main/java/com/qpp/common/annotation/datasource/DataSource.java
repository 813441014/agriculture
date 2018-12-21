package com.qpp.common.annotation.datasource;

import com.qpp.common.constant.enums.DataSourceType;

/**
 * @author qipengpai
 * @Title: DataSource
 * @ProjectName misscy
 * @Description: TODO 数据源注解
 * @date 10:30 2018/10/22
 */
public @interface DataSource {
    /**
     * 切换数据源名称
     * @return
     */
    DataSourceType value() default DataSourceType.MASTER;
}
