package com.qpp.admin.core.BootListener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @version 1.0.1
 * @Author qipengpai
 * @ClassName MyApplicationListener
 * @Description //TODO  通过监听，开辟线程，执行定时任务 当然 也可以执行其他
 * @Date 14:37 2018/12/18
 **/
@Slf4j
@Component
public class MyApplicationListener  implements ApplicationListener<ContextRefreshedEvent> {


  @Override
  public void onApplicationEvent(ContextRefreshedEvent event) {
    log.info("-------------bean初始化完毕-------------");
    /**
     * 通过线程开启数据库中已经开启的定时任务 灵感来自spring
     * spring boot继续执行 mythread开辟线程，延迟后执行
     */
    DataSourceJobThread myThread= (DataSourceJobThread) event.getApplicationContext().getBean(
        "dataSourceJobThread");
    myThread.start();
  }

}
