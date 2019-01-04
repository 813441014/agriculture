package com.qpp.admin.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @ClassName FeignConfig
 * @Description TODO
 * @Author qipengpai
 * @Date 2019/1/4 14:47
 * @Version 1.0.1
 */
@Configuration
public class FeignConfig {
    /**
     * @Author qipengpai
     * @Description //TODO 通过覆盖了默认的 Retryer Bean 更改了该 FeignClient 请求失败重试的策略，重试问隔为 100 毫秒，最大重试时间为 1 秒，重试次数为 5 次。
     * @Date 2019/1/4 17:18
     * @Param [] 
     * @return feign.Retryer
     * @throws 
     **/
    @Bean
    public Retryer feignRetryer () {
        return new Retryer.Default(100 , SECONDS.toMillis(1) , 5) ;
    }
}
