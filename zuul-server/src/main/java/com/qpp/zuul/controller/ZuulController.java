package com.qpp.zuul.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ZuulController
 * @Description TODO
 * @Author qipengpai
 * @Date 2018/12/29 17:15
 * @Version 1.0.1
 */
@RestController
@RequestMapping("/demo")
@Api(tags="zuul内部rest api")
public class ZuulController {

    @GetMapping("/hello")
    @ApiOperation(value="demo示例",notes="demo示例")
    @ApiImplicitParam(name="name",value="名称",example="oKong")
    public String hello(String name) {
        return "hi," + name + ",this is zuul api! ";
    }

}
