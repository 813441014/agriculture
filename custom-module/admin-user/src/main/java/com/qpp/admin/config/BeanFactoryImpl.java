package com.qpp.admin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0.1
 * @Author BeanName
 * @ClassName BeanFactoryImpl
 * @Description //TODO  让Bean获取配置他们的BeanFactory的引用。
 * @Date 15:37 2018/12/22
 **/
@Slf4j
@Configuration
public class BeanFactoryImpl implements BeanFactoryAware {


    /**
     实现了BeanFactoryAware接口的bean，可以直接通过beanfactory来访问spring的容器，
     当该bean被容器创建之后，会有一个相应的beanfactory的实例引用。
     该 接口有一个方法void setBeanFactory(BeanFactory beanFactory)方法通过这个方法的参数创建它的BeanFactory实例，
     实现了BeanFactoryAware接口，就可以让Bean拥有访问Spring容器的能力。
     * */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.info("BeanFactoryAware------->"+beanFactory);
    }
}

