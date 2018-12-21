package com.qpp.admin.core.BootListener;

import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author langmingsheng
 */
@Component
public class MyServletContextListener implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    System.out.println("-------contextInitialized-----------");
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    System.out.println("------------contextDestroyed-------------");
  }
}
