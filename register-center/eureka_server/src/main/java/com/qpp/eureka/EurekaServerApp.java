package com.qpp.eureka;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.ConfigurableApplicationContext;

/** 
* @author qipengpai
* @version 2018-12-18 22:50:29
* eureka高可用三台机器
*/
@EnableEurekaServer
@SpringCloudApplication
@EnableHystrixDashboard
@EnableTurbine
public class EurekaServerApp {

	public static void main(String[] args) {

//    	1本地启动采用此方法加载profiles文件
//		ConfigurableApplicationContext context = new SpringApplicationBuilder(UnieapEurekaServerApplication.class).
//				profiles("slave3").run(args);
//    	2服务器采用此方法 java -jar   --spring.profiles.active=slave3;
//    	 SpringApplication.run(DreiEurekaServerApp.class, args);
    	ConfigurableApplicationContext context = new SpringApplicationBuilder(EurekaServerApp.class).
				profiles("slave0").run(args);
	}

}