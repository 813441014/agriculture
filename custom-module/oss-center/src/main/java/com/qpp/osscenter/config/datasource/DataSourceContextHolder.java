package com.qpp.osscenter.config.datasource;

import com.qpp.common.constant.enums.DataSourceType;

/**
 *数据源切换类
 *构建一个DataSourceEnum容器，并提供了向其中设置和获取DataSorceEnum的方法
 *      由于我们的数据源信息要保证在同一线程下切换后不要被其他线程修改，所以我们将数据源信息保存在ThreadLocal共享中。
 */
public class DataSourceContextHolder {

    private DataSourceContextHolder(){}

    private static final ThreadLocal<DataSourceType> contextHolder = ThreadLocal.withInitial(DataSourceType.ADMIN);

    public static void setDatabaseType(DataSourceType type){
        contextHolder.set(type);
    }

    public static DataSourceType getDataSourceType(){
        return contextHolder.get();
    }

    public static void clearDataSourceType(){
        contextHolder.remove();
    }
}
