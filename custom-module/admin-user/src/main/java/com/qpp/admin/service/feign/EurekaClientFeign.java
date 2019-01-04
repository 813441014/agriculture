package com.qpp.admin.service.feign;

import com.qpp.admin.config.FeignConfig;
import com.qpp.admin.service.feign.hystrix.HiHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName EurekaClientFeign
 * @Description TODO Feign测试
 * @Author qipengpai
 * @Date 2019/1/4 17:17
 * @Version 1.0.1
 */
@FeignClient(value = "oss-center", configuration = FeignConfig.class,fallback = HiHystrix.class)
public interface EurekaClientFeign {

    @GetMapping (value = "/hi")
    String sayHiFromClientEureka(@RequestParam(value ="name") String name);
}
