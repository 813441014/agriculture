package com.qpp.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;

//@Controller
@RequestMapping("/redirect")
public class RedirectController {

    /**
     * @Author qipengpai
     * @Description //TODO 重定向 redis 客户端
     * @Date 13:00 2018/12/22
     * @Param [model]
     * @return org.springframework.web.servlet.view.RedirectView
     * @throws
     **/
    @Deprecated
    @GetMapping(value = "/redis_client")
    public String addMenu(Model model, HttpServletResponse response) throws Exception{
        response.setHeader("X-Frame-Options", "SAMEORIGIN");// 解决IFrame拒绝的问题
        response.sendRedirect("http://47.105.194.152:8080/redis-admin");
        return "";
    }

}
