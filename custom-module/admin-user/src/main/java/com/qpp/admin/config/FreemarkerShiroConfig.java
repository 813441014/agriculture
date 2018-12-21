package com.qpp.admin.config;

import com.jagregory.shiro.freemarker.ShiroTags;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author langmingsheng
 */
@Component
public class FreemarkerShiroConfig implements InitializingBean {

  @Autowired
  private freemarker.template.Configuration configuration;

  @Override
  public void afterPropertiesSet() throws Exception {
    configuration.setSharedVariable("shiro", new ShiroTags());
  }
}
