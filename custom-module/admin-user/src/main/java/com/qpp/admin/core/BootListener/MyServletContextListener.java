package com.qpp.admin.core.BootListener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * @version 1.0.1
 * @Author qipengpai
 * @ClassName MyServletContextListener
 * @Description //TODO  暂无用处
 * @Date 14:37 2018/12/18
 **/
@Slf4j
@Component
public class MyServletContextListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    log.info("-------contextInitialized-----------");
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    log.info("------------contextDestroyed-------------");
  }
}
