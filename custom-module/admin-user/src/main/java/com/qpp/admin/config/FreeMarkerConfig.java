package com.qpp.admin.config;

import freemarker.template.TemplateException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.Properties;

/**
 * @version 1.0.1
 * @Author qipengpai
 * @ClassName FreeMarkerConfig
 * @Description //TODO  FreeMarker配置类
 * @Date 15:37 2018/12/22
 **/
@Configuration
public class FreeMarkerConfig{

  @Bean
  public FreeMarkerConfigurer freemarkerConfig() throws IOException, TemplateException {
    FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
    factory.setTemplateLoaderPath("classpath:/templates/");
    factory.setDefaultEncoding("UTF-8");
    factory.setPreferFileSystemAccess(false);
    FreeMarkerConfigurer result = new FreeMarkerConfigurer();
    freemarker.template.Configuration configuration = factory.createConfiguration();
    configuration.setClassicCompatible(true);
    result.setConfiguration(configuration);
    Properties settings = new Properties();
    settings.put("template_update_delay", "0");
    settings.put("default_encoding", "UTF-8");
    settings.put("number_format", "0.######");
    settings.put("classic_compatible", true);
    settings.put("template_exception_handler", "ignore");
    result.setFreemarkerSettings(settings);
    return result;
  }

}