package com.qpp.admin.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qipengpai
 */
@Configuration
//@EnableTransactionManagement
public class DruidConfig {
  /**
   * 读取yml或者properties配置文件
   */
  @Autowired
  private Environment env;
  @Value("${spring.datasource.url}")
  private String url;
  @Value("${spring.datasource.username}")
  private String username;
  @Value("${spring.datasource.password}")
  private String password;
  @Value("${spring.datasource.filters}")
  private String filters;
  @Value("${spring.datasource.driver-class-name}")
  private String driverClassName;
  @Value("${spring.datasource.initialSize}")
  private int initialSize;
  @Value("${spring.datasource.minIdle}")
  private int minIdle;

  @Bean
  @Primary
  public DataSource getDataSource(){
    System.out.println("-------初始化druid------");
    DruidDataSource datasource = new DruidDataSource();

    datasource.setUrl(url);
    datasource.setUsername(username);
    datasource.setPassword(password);
    datasource.setDriverClassName(driverClassName);
    datasource.setInitialSize(initialSize);
    datasource.setMinIdle(minIdle);
    try {
      datasource.setFilters(filters);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return datasource;
  }

  @Bean
  public FilterRegistrationBean filterRegistrationBean() {
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    filterRegistrationBean.setFilter(new WebStatFilter());
    filterRegistrationBean.addUrlPatterns("/*");
    filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.nmpocket.css,*.ico,/druid/*,*.html");
    DelegatingFilterProxy proxy = new DelegatingFilterProxy();
    proxy.setTargetFilterLifecycle(true);
    proxy.setTargetBeanName("shiroFilter");

    filterRegistrationBean.setFilter(proxy);
    return filterRegistrationBean;
  }

  @Bean
  public ServletRegistrationBean druidServlet() {
    ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
    servletRegistrationBean.setServlet(new StatViewServlet());
    servletRegistrationBean.addUrlMappings("/druid/*");
    Map<String, String> initParameters = new HashMap<String, String>();
    initParameters.put("resetEnable", "false");
    initParameters.put("allow", "");
    servletRegistrationBean.setInitParameters(initParameters);
    return servletRegistrationBean;
  }

  /**
   * 根据动态数据源配置，创建sqlSessionFactory
   * @return
   * @throws Exception
   */
  @Primary
  @Bean("sqlSessionFactory")
  public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(dataSource);
    //sessionFactory.setTransactionFactory(new MultiDataSourceTransactionFactory());
    sessionFactory.setTypeAliasesPackage(env.getProperty("mybatis.type-aliases-package"));//对应yml中mybatis的bean目录
    sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(env.getProperty("mybatis.mapper-locations")));//对应yml中mybatis的xml
    return sessionFactory.getObject();

  }

  @Bean("transactionManager")
  public DataSourceTransactionManager transactionManager(DataSource dataSource) throws Exception {
    return new DataSourceTransactionManager(dataSource);
  }
/*
  @Bean
  public DruidStatInterceptor getDruidStatInterceptor(){
    return new DruidStatInterceptor();
  }

  @Bean
  @Scope("prototype")
  public JdkRegexpMethodPointcut getJdkRegexpMethodPointcut(){
    JdkRegexpMethodPointcut pointcut=new JdkRegexpMethodPointcut();
    String[] str={"com.len.service.*","com.len.mapper.*"};
    pointcut.setPatterns(str);
    return pointcut;
  }*/

}
