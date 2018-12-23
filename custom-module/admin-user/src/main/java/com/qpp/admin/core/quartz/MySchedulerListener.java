package com.qpp.admin.core.quartz;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class MySchedulerListener {

    @Bean(name ="schedulerFactoryBean")
    public SchedulerFactoryBean schedulerFactory(MyJobFactory myJobFactory) {
        SchedulerFactoryBean bean = new SchedulerFactoryBean();
        bean.setJobFactory(myJobFactory);  
        return bean;  
    }  
  
}  