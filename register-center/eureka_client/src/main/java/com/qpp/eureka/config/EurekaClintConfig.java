package com.qpp.eureka.config;

import com.netflix.discovery.DiscoveryClient.DiscoveryClientOptionalArgs;
import com.qpp.eureka.filter.IpCilentFilter;
import com.qpp.eureka.filter.RequestAuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class EurekaClintConfig {
	@Bean
	public DiscoveryClientOptionalArgs discoveryClientOptionalArgs() {
	    DiscoveryClientOptionalArgs discoveryClientOptionalArgs = new DiscoveryClientOptionalArgs();
	    discoveryClientOptionalArgs.setAdditionalFilters(Collections.singletonList(new IpCilentFilter()));
	    return discoveryClientOptionalArgs;
	}
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
	    FilterRegistrationBean registration = new FilterRegistrationBean(new RequestAuthFilter());
	    registration.addUrlPatterns("/*");
	    return registration;
	}
	 
}
