package com.qpp.basic.util;

import com.qpp.basic.base.bean.CurrentUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

/**
 * @author qipengpai
 *
 * 管理工具类
 */
public class CommonUtil {

  private CommonUtil(){}

  /**
   * 获取当前用户
   */
  public static CurrentUser getUser() {
    org.apache.shiro.subject.Subject subject = SecurityUtils.getSubject();
    Session session = subject.getSession();
    return (CurrentUser) session.getAttribute("curentUser");
  }
}
