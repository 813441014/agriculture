package com.qpp.admin.core.shiro;

import com.alibaba.fastjson.JSONArray;
import com.qpp.admin.entity.system.SysMenu;
import com.qpp.admin.entity.system.SysRole;
import com.qpp.admin.entity.system.SysUser;
import com.qpp.admin.service.system.MenuService;
import com.qpp.admin.service.system.RoleMenuService;
import com.qpp.admin.service.system.RoleUserService;
import com.qpp.admin.service.system.SysUserService;
import com.qpp.basic.base.bean.CurrentMenu;
import com.qpp.basic.base.bean.CurrentRole;
import com.qpp.basic.base.bean.CurrentUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/**
 * @version 1.0.1
 * @Author qipengpai
 * @ClassName LoginRealm
 * @Description //TODO realm
 * @Date 11:37 2018/12/19
 **/
public class LoginRealm extends AuthorizingRealm{


  @Autowired
  private SysUserService userService;

  @Autowired
  private MenuService menuService;

  @Autowired
  private RoleUserService roleUserService;

  @Autowired
  private RoleMenuService roleMenuService;

  /**
   * @Author qipengpai
   * @Description //TODO 获取认证
   * @Date 16:49 2018/12/22
   * @Param [principalCollection]
   * @return org.apache.shiro.authz.AuthorizationInfo
   * @throws 
   **/
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    String name= (String) principalCollection.getPrimaryPrincipal();
    //根据用户获取角色 根据角色获取所有按钮权限
    CurrentUser cUser= (CurrentUser) ShiroUtil.getSession().getAttribute("curentUser");
    for(CurrentRole cRole:cUser.getCurrentRoleList()){
      info.addRole(cRole.getId());
    }
    for(CurrentMenu cMenu:cUser.getCurrentMenuList()){
      if(!StringUtils.isEmpty(cMenu.getPermission()))
        info.addStringPermission(cMenu.getPermission());
    }
    return info;
  }

  /**
   * @Author qipengpai
   * @Description //TODO 获取授权
   * @Date 16:50 2018/12/22
   * @Param [authenticationToken]
   * @return org.apache.shiro.authc.AuthenticationInfo
   * @throws 
   **/
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
          throws AuthenticationException {
    UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
    String name=upToken.getUsername();
    String username=(String)authenticationToken.getPrincipal();
    SysUser s=null;
    try {
      s = userService.login(username);
    }catch (Exception e){
      e.printStackTrace();
    }
    if(s==null){
      throw new UnknownAccountException("账户密码不正确");
    }else{
      CurrentUser currentUser=new CurrentUser(s.getId(),s.getUsername(),s.getAge(),s.getEmail(),s.getPhoto(),s.getRealName());
      Subject subject = ShiroUtil.getSubject();
      /**角色权限封装进去*/
      //根据用户获取菜单
      List<SysMenu> menuList=new ArrayList<>(new HashSet<>(menuService.getUserMenu(s.getId())));
      JSONArray json=menuService.getMenuJsonByUser(menuList);
      Session session= subject.getSession();
      session.setAttribute("menu",json);
      CurrentMenu currentMenu=null;
      List<CurrentMenu> currentMenuList=new ArrayList<>();
      List<SysRole> roleList=new ArrayList<>();
      for(SysMenu m:menuList){
        currentMenu=new CurrentMenu(m.getId(),m.getName(),m.getPId(),m.getUrl(),m.getOrderNum(),m.getIcon(),m.getPermission(),m.getMenuType(),m.getNum());
        currentMenuList.add(currentMenu);
        roleList.addAll(m.getRoleList());
      }
      roleList= new ArrayList<>(new HashSet<>(roleList));
      List<CurrentRole> currentRoleList=new ArrayList<>();
      CurrentRole role=null;
      for(SysRole r:roleList){
        role=new CurrentRole(r.getId(),r.getRoleName(),r.getRemark());
        currentRoleList.add(role);
      }
      currentUser.setCurrentRoleList(currentRoleList);
      currentUser.setCurrentMenuList(currentMenuList);
      session.setAttribute("curentUser",currentUser);
    }
    ByteSource byteSource=ByteSource.Util.bytes(username);
    return new SimpleAuthenticationInfo(username,s.getPassword(), byteSource, getName());
  }
}
