package com.qpp.admin.config;

import com.jagregory.shiro.freemarker.ShiroTags;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;


/**
 * @version 1.0.1
 * @Author qipengpai
 * @ClassName FreemarkerShiroConfig
 * @Description //TODO   Freemarker集成Shiro标签
 * @Date 15:37 2018/12/22
 **/
@Component
public class FreemarkerShiroConfig implements InitializingBean {

  @Autowired
  private freemarker.template.Configuration configuration;


  @Autowired
  private FreeMarkerViewResolver resolver;

  @Override
  public void afterPropertiesSet() throws Exception {
    //TODO  加上这句后，可以在页面上使用shiro标签
    configuration.setSharedVariable("shiro", new ShiroTags());
    //TODO 加上这句后，可以在页面上用${context.contextPath}获取contextPath
    //resolver.setRequestContextAttribute("context");
  }
}
