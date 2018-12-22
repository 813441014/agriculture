package com.qpp.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @version 1.0.1
 * @Author qipengpai
 * @ClassName WebMvcConfig
 * @Description //TODO  Spring内部的一种配置方式，采用JavaBean的形式来代替传统的xml配置文件形式进行针对框架个性化定制
 * @Date 12:37 2018/12/22
 **/
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {


    @Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        return converter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(responseBodyConverter());
    }

    //TODO 静态资源处理
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        super.addResourceHandlers(registry);
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("/plugin/**","/static/**")
            .addResourceLocations("classpath:/plugin/","classpath:/static/");
    }

    /**
     * 以前要访问一个页面需要先创建个Controller控制类，再写方法跳转到页面
     * 在这里配置后就不需要那么麻烦了，直接访问http://localhost:8080/toLogin就跳转到login.ftl页面了
     * @param registry
     */


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/redirect/redis_client").setViewName("login");
    }

    /**
     * WebMvcConfigurer配置类其实是Spring内部的一种配置方式，
     * 采用JavaBean的形式来代替传统的xml配置文件形式进行针对框架个性化定制。
     * 基于java-based方式的spring mvc配置，需要创建一个配置类并实现WebMvcConfigurer 
     * 接口，WebMvcConfigurerAdapter 抽象类是对WebMvcConfigurer接口的简单抽象（增加了一些默认实现），
     * 但在在SpringBoot2.0及Spring5.0中WebMvcConfigurerAdapter已被废弃 。
     * 官方推荐直接实现WebMvcConfigurer或者直接继承WebMvcConfigurationSupport
     */
}
