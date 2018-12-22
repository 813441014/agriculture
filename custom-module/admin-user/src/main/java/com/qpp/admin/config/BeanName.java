package com.qpp.admin.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0.1
 * @Author qipengpai
 * @ClassName BeanName
 * @Description //TODO  让Bean获取自己在BeanFactory配置中的名字（根据情况是id或者name）
 * @Date 15:37 2018/12/22
 **/
@Slf4j
@Configuration
public class BeanName implements BeanNameAware {

    /**
     * Spring自动调用。并且会在Spring自身完成Bean配置之后，
     * 且在调用任何Bean生命周期回调（初始化或者销毁）方法之前就调用这个方法。
     * 换言之，在程序中使用BeanFactory.getBean(String beanName)之前，Bean的名字就已经设定好了
     * */
    @Override
    public void setBeanName(String name) {
        log.info("BeanNameAware-------->:" + name);
    }
}
