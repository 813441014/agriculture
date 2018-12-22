package com.qpp.admin.core.shiro;


import com.qpp.basic.base.bean.CurrentUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * @version 1.0.1
 * @Author qipengpai
 * @ClassName ShiroUtil
 * @Description //TODO Shiro工具类
 * @Date 12:37 2018/12/19
 **/
public class ShiroUtil {

    public static Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    public static Session getSession(){
        return getSubject().getSession();
    }

    public static CurrentUser getCurrentUse(){
        return (CurrentUser) getSession().getAttribute("curentUser");
    }

}
