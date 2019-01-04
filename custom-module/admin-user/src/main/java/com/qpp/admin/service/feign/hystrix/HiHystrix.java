package com.qpp.admin.service.feign.hystrix;

import com.qpp.admin.service.feign.EurekaClientFeign;
import org.springframework.stereotype.Component;

/**
 * @ClassName HiHystrix
 * @Description TODO Hystrix 测试
 * @Author qipengpai
 * @Date 2019/1/4 17:21
 * @Version 1.0.1
 */
@Component
public class HiHystrix implements EurekaClientFeign {
    @Override
    public String sayHiFromClientEureka(String name) {
        return "hi ,"+name +",sorry, error!";
    }
}
