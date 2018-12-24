package com.qpp.admin.config;

import com.qpp.admin.core.filter.PermissionFilter;
import com.qpp.admin.core.filter.VerfityCodeFilter;
import com.qpp.admin.core.shiro.LoginRealm;
import com.qpp.admin.core.shiro.RetryLimitCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @version 1.0.1
 * @Author qipengpai
 * @ClassName ShiroConfig
 * @Description //TODO  Shiro配置类
 * @Date 14:37 2018/12/18
 **/
@Configuration
public class ShiroConfig {

  //TODO 登录次数
  @Bean
  public RetryLimitCredentialsMatcher getRetryLimitCredentialsMatcher(){
    RetryLimitCredentialsMatcher rm=new RetryLimitCredentialsMatcher(getCacheManager(),"5");
    rm.setHashAlgorithmName("md5");
    rm.setHashIterations(4);
    return rm;

  }
  @Bean(name = "loginRealm")
  public LoginRealm getLoginRealm(){
    LoginRealm realm= new LoginRealm();
    //TODO 设置资格证书
    realm.setCredentialsMatcher(getRetryLimitCredentialsMatcher());
    return realm;
  }

  //TODO 加载ehcache缓存
  @Bean
  public EhCacheManager getCacheManager(){
    EhCacheManager ehCacheManager=new EhCacheManager();
    ehCacheManager.setCacheManagerConfigFile("classpath:ehcache/ehcache.xml");
    return ehCacheManager;
  }

  @Bean
  public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
    return new LifecycleBeanPostProcessor();
  }

  @Bean(name="securityManager")
  public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("loginRealm") LoginRealm loginRealm){
    DefaultWebSecurityManager dwm=new DefaultWebSecurityManager();
    dwm.setRealm(loginRealm);
    dwm.setCacheManager(getCacheManager());
    return dwm;
  }

  @Bean
  public PermissionFilter getPermissionFilter(){
    PermissionFilter pf=new PermissionFilter();
    return pf;
  }

  @Bean
  public VerfityCodeFilter getVerfityCodeFilter(){
    VerfityCodeFilter vf= new VerfityCodeFilter();
    vf.setFailureKeyAttribute("shiroLoginFailure");
    vf.setJcaptchaParam("code");
    vf.setVerfitiCode(true);
    return vf;
  }

  @Bean
  public FilterRegistrationBean delegatingFilterProxy(){
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
    filterRegistrationBean.addUrlPatterns("/*");
    filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.nmpocket.css,*.ico,/druid/*,*.html");
    DelegatingFilterProxy proxy = new DelegatingFilterProxy();
    proxy.setTargetFilterLifecycle(true);
    proxy.setTargetBeanName("shiroFilter");

    filterRegistrationBean.setFilter(proxy);
    return filterRegistrationBean;
  }


  @Bean(name = "shiroFilter")
  public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
    ShiroFilterFactoryBean sfb = new ShiroFilterFactoryBean();
    sfb.setSecurityManager(securityManager);
    sfb.setLoginUrl("/login");
    sfb.setUnauthorizedUrl("/goLogin");
    Map<String, Filter> filters=new HashMap<>();
    filters.put("per",getPermissionFilter());
    filters.put("verCode",getVerfityCodeFilter());
    sfb.setFilters(filters);
    Map<String, String> filterMap = new LinkedHashMap<>();
    filterMap.put("/login","verCode,anon");
    //filterMap.put("/login","anon");
    filterMap.put("/getCode","anon");
//    filterMap.put("/product/**","anon");
    filterMap.put("/logout","logout");
    filterMap.put("/plugin/**","anon");
    filterMap.put("/user/**","per");
    filterMap.put("/**","authc");
    sfb.setFilterChainDefinitionMap(filterMap);
    return sfb;
  }

  @Bean
  public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
    DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
    advisorAutoProxyCreator.setProxyTargetClass(true);
    return advisorAutoProxyCreator;
  }

  @Bean
  public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
    AuthorizationAttributeSourceAdvisor as=new AuthorizationAttributeSourceAdvisor();
    as.setSecurityManager(securityManager);
    return as;
  }


/**
 * 限制用户登录尝试次数，防止多次尝试，暴力破解密码情况出现。
 * 要限制用户登录尝试次数，必然要对用户名密码验证失败做记录，Shiro中用户名密码的验证交给了CredentialsMatcher
 * 所以在CredentialsMatcher里面检查，记录登录次数是最简单的做法。
 * Shiro天生和Ehcache是一对好搭档，无论是单机还是集群，都可以在Ehcache中存储登录尝试次数信息。
 * 现在介绍一个简单的登录次数验证做法，实现一个RetryLimitCredentialsMatchers继承至HashedCredentialsMatcher，
 * 加入缓存，在每次验证用户名密码之前先验证用户名尝试次数，如果超过5次就抛出尝试过多异常，
 * 否则验证用户名密码，验证成功把尝试次数清零，不成功则直接退出。
 * 这里依靠Ehcache自带的timeToIdleSeconds来保证锁定时间
 */

}
