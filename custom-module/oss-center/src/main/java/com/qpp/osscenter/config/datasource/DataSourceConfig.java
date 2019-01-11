package com.qpp.osscenter.config.datasource;

import com.alibaba.druid.support.http.StatViewServlet;
import com.qpp.common.constant.enums.DataSourceType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 创建数据源
 */
@Configuration
public class DataSourceConfig {


    /**
     * 正常使用路径
     */
    @Bean(name = "dataSourceFile")//指定名称，未指定默认为方法名
    @RefreshScope //refresh
    @ConfigurationProperties(prefix = "spring.datasource.file")//prefix对应yml中的前缀
    public DataSource dataSourceAdmin(){
        return DataSourceBuilder.create().build();
    }
    /**
     * 更多数据源选择
     */
    @Bean(name = "dataSourceFileHq")
    @RefreshScope //refresh
    @ConfigurationProperties(prefix = "spring.datasource.filehq")
    public DataSource dataSourceLog(){
        return DataSourceBuilder.create().build();
    }


    /**
     * @Author qipengpai
     * @Description //TODO 动态切换数据源类 指定默认数据源
     * @Date 2018/12/24 9:52
     * @Param [admin, log]
     * @return com.qpp.admin.config.datasource.DynamicDataSource
     * @throws
     **/
    @Primary //指定默认数据源，此处为第一个是dataSourceUser
    @Bean("dataSource")
    public DynamicDataSource dataSource(@Qualifier("dataSourceFile") DataSource file,
                                        @Qualifier("dataSourceFileHq") DataSource file_hq) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.FILE, file);
        targetDataSources.put(DataSourceType.FILE_HQ, file_hq);
        //采用是想AbstractRoutingDataSource的对象包装多数据源
        dynamicDataSource.setTargetDataSources(targetDataSources);
        // 设置默认的数据源
        dynamicDataSource.setDefaultTargetDataSource(file);
        return dynamicDataSource;

    }

    /**
     * @Author qipengpai
     * @Description //TODO 根据动态数据源配置，创建sqlSessionFactory
     * @Date 2018/12/24 9:55
     * @Param [dataSource]
     * @return org.apache.ibatis.session.SqlSessionFactory
     * @throws
     **/
    @Primary
    @Bean("sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSourceFile") DataSource file,
                                               @Qualifier("dataSourceFileHq") DataSource file_hq,
                                               Environment env) throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        // 指定数据源(这个必须有，否则报错)
        sessionFactory.setDataSource(this.dataSource(file,file_hq));
        //对应yml中mybatis的bean目录
        sessionFactory.setTypeAliasesPackage(env.getProperty("mybatis.type-aliases-package"));
        //对应yml中mybatis的xml
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapper-locations")));
        return sessionFactory.getObject();

    }


    /**
     * 根据动态数据源配置，创建transactionManager
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean("transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DynamicDataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        Map<String, String> initParameters = new HashMap<>();
        initParameters.put("resetEnable", "false");
        initParameters.put("allow", "");
        servletRegistrationBean.setInitParameters(initParameters);
        return servletRegistrationBean;
    }
}
