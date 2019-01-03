package com.qpp.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

/**
 * @ClassName RibbonConfig
 * @Description TODO Ribbon RestTemplate.配置类
 * @Author qipengpai
 * @Date 2019/1/3 22:50
 * @Version 1.0.1
 */
@Configuration
public class RibbonConfig {

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

   /* @Autowired
    private LoadBalancerClient loadBalancer;
    @GetMapping("/testRibbon")
    public String testRibbon () {
        ServiceInstance  instance = loadBalancer.choose("eurekaclient");
        return instance.getHost()+":"+instance.getPort();
    }*/
}