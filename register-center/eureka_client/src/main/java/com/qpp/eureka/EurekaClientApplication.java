package com.qpp.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/** 
* @author 作者 owen E-mail: 624191343@qq.com
* @version 创建时间：2017年11月28日 下午21:52:43 
* 类说明
* 服务提供商 eureka的客户端程序 
*/

@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
public class EurekaClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClientApplication.class, args);
    }

      /*public static void main(String[] args) {

          ConfigurableApplicationContext context =new SpringApplicationBuilder(EurekaClientApplication.class)
                  .properties("server.port="+7767).run(args);
      }*/
    /**
     * 使用ribbon负载均衡器，用于服务提供商的负载均衡
     * @return
     */
    @Bean
	@LoadBalanced
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}


    //    eureka restful api
//            查看eureka的状态
//    http://127.0.0.1:7768/eureka/status
//    查看有多少服务
//    http://127.0.0.1:7778/eureka/apps
//
//    查看某个服务多少实例
//    http://127.0.0.1:7768/eureka/apps?name=OPEN-EUREKA-CLIENT
//    查看某个实例的状态
//    http://127.0.0.1:7768/eureka/apps/OPEN-EUREKA-CLIENT/open-eureka-client:192.168.45.1:7778
//
//    暂停微服务
//    http://127.0.0.1:7768/pause
//    重启微服务
//    http://127.0.0.1:7768/resume
}
