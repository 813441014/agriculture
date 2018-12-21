package com.qpp.eureka.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/** 
* @author 作者 owen E-mail: 624191343@qq.com
* @version 创建时间：2017年11月28日 下午21:52:43
* 类说明 
*/
@RestController
public class HelloController {

	@Resource
	private RestTemplate restTemplate ;
	
	@GetMapping("/hello")
	public String hello(  HttpServletRequest request){
		
		return  "hello:	" +request.getHeader("Authorization");
	}
	
	@GetMapping("/route")
	public String hello1(){
		String resp = this.restTemplate.getForObject("http://eureka_client/client/hello", String.class);
		return resp;
	}
}
