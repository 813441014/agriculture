package com.qpp.db.config;

/**
 *数据源切换类
 *构建一个DataSourceEnum容器，并提供了向其中设置和获取DataSorceEnum的方法
 *      由于我们的数据源信息要保证在同一线程下切换后不要被其他线程修改，所以我们将数据源信息保存在ThreadLocal共享中。
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<DataSourceKey> contextHolder = new ThreadLocal<DataSourceKey>(){
        @Override
        protected DataSourceKey initialValue() {
            return DataSourceKey.core;//初始为用户数据源
        }
    };

    //private static final ThreadLocal<DataSourceKey> contextHolder = new ThreadLocal<>();

    public static DataSourceKey getDataSourceKey() {
        return contextHolder.get();
    }

    public static void setDataSourceKey(DataSourceKey type) {
        contextHolder.set(type);
    }

    public static void clearDataSourceKey() {
        contextHolder.remove();
    }

}
