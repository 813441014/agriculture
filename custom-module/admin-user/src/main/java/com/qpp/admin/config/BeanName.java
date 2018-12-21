package com.qpp.admin.config;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanName implements BeanNameAware {

    @Override
    public void setBeanName(String name) {
        System.out.println("BeanNameAware-------->:" + name);
    }
}
