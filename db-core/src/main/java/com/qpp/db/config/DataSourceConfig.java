package com.qpp.db.config;


import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


/**
 * @author 作者 owen E-mail: 624191343@qq.com
 * @version 创建时间：2017年04月23日 下午20:01:06 类说明
 * central-platform 
 * 在设置了spring.datasource.enable.dynamic 等于true是开启多数据源
 */
@Configuration
@ConditionalOnProperty(name = {"spring.datasource.dynamic.enable"}, matchIfMissing = false, havingValue = "true")
public class DataSourceConfig {

    @Autowired
    private Environment env;

//	# Druid 数据源 1 配置，继承spring.datasource.druid.* 配置，相同则覆盖
//	...
//	spring.datasource.druid.one.max-active=10
//	spring.datasource.druid.one.max-wait=10000
//	...
//
//	# Druid 数据源 2 配置，继承spring.datasource.druid.* 配置，相同则覆盖
//	...
//	spring.datasource.druid.two.max-active=20
//	spring.datasource.druid.two.max-wait=20000
//	...
//	强烈注意：Spring Boot 2.X 版本不再支持配置继承，多数据源的话每个数据源的所有配置都需要单独配置，否则配置不会生效

//	创建数据源


//	所有引入db-core的模块都需要一个核心库，可以是user-center，也可以是oauth-center,file-center ,sms-center
	@Bean
	@ConfigurationProperties("spring.datasource.druid.core")
	public DataSource dataSourceCore(){
	    return DruidDataSourceBuilder.create().build();
	}
//	所有的核心库共享一个日志中心模块，改模块不采用mysql中的innodb引擎，采用归档引擎
	@Bean
	@ConfigurationProperties("spring.datasource.druid.log")
	public DataSource dataSourceLog(){
	    return DruidDataSourceBuilder.create().build();
	}
    //	用户
    @Bean
    @ConfigurationProperties("spring.datasource.druid.user")
    public DataSource dataSourceUser(){
        return DruidDataSourceBuilder.create().build();
    }


    // 只需要纳入动态数据源到spring容器
    @Bean
    @Primary
    public DynamicDataSource dataSource(@Qualifier("dataSourceCore") DataSource core,
                                 @Qualifier("dataSourceLog") DataSource log,
                                 @Qualifier("dataSourceUser") DataSource user) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        Map targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceKey.user, user);
        targetDataSources.put(DataSourceKey.log, log);
        targetDataSources.put(DataSourceKey.core, core);
        //采用是想AbstractRoutingDataSource的对象包装多数据源
        dynamicDataSource.setTargetDataSources(targetDataSources);
        // 设置默认的数据源
        dynamicDataSource.setDefaultTargetDataSource(core);
        return dynamicDataSource;
    }

    
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSourceCore") DataSource core,
                                               @Qualifier("dataSourceLog") DataSource log,
                                               @Qualifier("dataSourceUser") DataSource user)
            throws Exception {
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(this.dataSource(core, log,user));
        //加载mybatis配置文件
        sessionFactory.setConfigLocation(new ClassPathResource("mybatis.cfg.xml"));
        sessionFactory.setTypeAliasesPackage(env.getProperty("mapper.type-aliases-package"));//对应yml中mybatis的bean目录
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mapper.mapper-locations")));//对应yml中mybatis的xml
        return sessionFactory.getObject();
    }

    
    @Bean // 将数据源纳入spring事物管理
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource")  DynamicDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
   
}
