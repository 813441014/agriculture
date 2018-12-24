package com.qpp.admin.controller;

import com.alibaba.fastjson.JSONArray;

import com.qpp.common.annotation.log.Log;
import com.qpp.admin.core.shiro.ShiroUtil;
import com.qpp.admin.entity.system.SysMenu;
import com.qpp.admin.entity.system.SysUser;
import com.qpp.admin.service.system.MenuService;
import com.qpp.admin.service.system.SysUserService;
import com.qpp.basic.base.bean.CurrentUser;
import com.qpp.basic.util.CommonUtil;
import com.qpp.basic.util.VerifyCodeUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author qipengpai
 * 登录、退出页面
 */
@Controller
public class LoginController {

    private static final String MAIN = "/main/main";
    private static final String LOGIN = "/login";
    private static final String MESSAGE = "message";

    @Autowired
    private SysUserService userService;

    @Autowired
    private MenuService menuService;

    @GetMapping(value = "")
    public String loginInit() {
        return loginCheck();
    }

    @GetMapping(value = "goLogin")
    public String goLogin(Model model, ServletRequest request) {
        Subject sub = SecurityUtils.getSubject();
        if (sub.isAuthenticated()) {
            return MAIN;
        } else {
            model.addAttribute(MESSAGE, "请重新登录");
            return LOGIN;
        }
    }

    @GetMapping(value = LOGIN)
    public String loginCheck() {
        Subject sub = SecurityUtils.getSubject();
        Boolean flag2 = sub.isRemembered();
        boolean flag = sub.isAuthenticated() || flag2;
        Session session = sub.getSession();
        if (flag) {
            return MAIN;
        }
        return LOGIN;
    }

    /**
     * @Author qipengpai
     * @Description //TODO 登录动作
     * @Date 2018/12/23 18:06
     * @Param [user, model, rememberMe, request]
     * @return java.lang.String
     * @throws
     **/
    @ApiOperation(value = LOGIN, httpMethod = "POST", notes = "登录method")
    @PostMapping(value = LOGIN)
    public String login(SysUser user, Model model, String rememberMe, HttpServletRequest request) {
        String codeMsg = (String) request.getAttribute("shiroLoginFailure");
        if("code.error".equals(codeMsg)){
            model.addAttribute(MESSAGE, "验证码错误");
            return LOGIN;
        }
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername().trim(), user.getPassword());
        Subject subject = ShiroUtil.getSubject();
        String msg = null;
        try{
            subject.login(token);
            //subject.hasRole("admin");
            if(subject.isAuthenticated()){
                SysUser sysUser = this.userService.login(user.getUsername().trim());
                if(sysUser != null){
                    if ("third".equals(sysUser.getPhoto())) {
                        byte delFlag = 1;
                        if (sysUser.getDelFlag() == delFlag) {
                            msg = "账号状态异常，请联系管理员";
                        } else {
                            request.getSession(false).setAttribute("currentUser", sysUser);
                            return MAIN;
                        }
                    }else{
                        request.getSession(false).setAttribute("currentUser", sysUser);
                        return MAIN;
                    }
                }else{
                    msg = "登陆失败！";
                }
            }
        } catch (UnknownAccountException e) {
            msg = "用户名/密码错误";
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            msg = "用户名/密码错误";
        } catch (ExcessiveAttemptsException e) {
            msg = "登录失败多次，账户锁定10分钟";
        }
        if (msg != null) {
            model.addAttribute(MESSAGE, msg);
        }
        return LOGIN;
    }

    @Log(desc = "用户退出平台")
    @GetMapping(value = "/logout")
    public String logout() {
        Subject sub = SecurityUtils.getSubject();
        sub.logout();
        return LOGIN;
    }

    /**
     * 组装菜单json格式
     * update by 17/12/13
     *
     * @return
     */
    public JSONArray getMenuJson() {
        List<SysMenu> mList = menuService.getMenuNotSuper();
        JSONArray jsonArr = new JSONArray();
        for (SysMenu sysMenu : mList) {
            SysMenu menu = getChild(sysMenu.getId());
            jsonArr.add(menu);
        }
        return jsonArr;
    }

    public SysMenu getChild(String id) {
        SysMenu sysMenu = menuService.selectByPrimaryKey(id);
        List<SysMenu> mList = menuService.getMenuChildren(id);
        for (SysMenu menu : mList) {
            SysMenu m = getChild(menu.getId());
            sysMenu.addChild(m);
        }
        return sysMenu;
    }


    @GetMapping(value = "/getCode")
    public void getYzm(HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpg");

            //生成随机字串
            String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
            //存入会话session
            HttpSession session = request.getSession(true);
            session.setAttribute("_code", verifyCode.toLowerCase());
            //生成图片
            int w = 146, h = 33;
            VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PostMapping(value = "/validateLogin")
    @ResponseBody
    public String validateLogin() {
        CurrentUser currentUser = CommonUtil.getUser();
        if (currentUser == null) {
            return "-1";//数据异常
        }

        if (currentUser.getPhoto()==null){
            return "1";
        }
        else{
            if (currentUser.getPhoto().equals("third")) {
                if (currentUser.getDelFlag() == 1) {
                    return "-2";//账户禁用状态
                } else {
                    return "1";//正常使用
                }
            }
            return "1";
        }
    }
}
