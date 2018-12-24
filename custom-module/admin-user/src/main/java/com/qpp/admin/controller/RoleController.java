package com.qpp.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.qpp.common.annotation.log.Log;
import com.qpp.admin.entity.system.SysRole;
import com.qpp.admin.entity.system.SysRoleMenu;
import com.qpp.admin.entity.system.SysRoleUser;
import com.qpp.admin.service.system.MenuService;
import com.qpp.admin.service.system.RoleMenuService;
import com.qpp.admin.service.system.RoleService;
import com.qpp.admin.service.system.RoleUserService;
import com.qpp.basic.base.BaseController;
import com.qpp.basic.exception.MyException;
import com.qpp.basic.util.JsonUtil;
import com.qpp.common.utils.bean.BeanUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author qipengpai
 * 角色业务
 */
@Slf4j
@Controller
@RequestMapping(value = "/role")
public class RoleController extends BaseController {

  @Autowired
  private RoleService roleService;

  @Autowired
  private RoleUserService roleUserService;

  @Autowired
  private MenuService menuService;

  @Autowired
  private RoleMenuService roleMenuService;

  @GetMapping(value = "showRole")
  @RequiresPermissions(value = "role:show")
  public String showRole(Model model){
    return "/system/role/roleList";
  }

  @ApiOperation(value = "/showRoleList", httpMethod = "GET", notes = "展示角色")
  @GetMapping(value = "showRoleList")
  @ResponseBody
  @RequiresPermissions("role:show")
  public String showRoleList(SysRole role, Model model, String page, String limit){
   return roleService.show(role,Integer.valueOf(page),Integer.valueOf(limit));
  }

  @GetMapping(value = "showAddRole")
  public String addRole(Model model) {
    JSONArray jsonArray=menuService.getTreeUtil(null);
    model.addAttribute("menus",jsonArray.toJSONString());
    return "/system/role/add-role";
  }

  @ApiOperation(value = "/addRole", httpMethod = "POST", notes = "添加角色")
  @Log(desc = "添加角色" ,type = Log.LOG_TYPE.ADD)
  @PostMapping(value = "addRole")
  @ResponseBody
  public String addRole(SysRole sysRole,String[] menus){
    if(StringUtils.isEmpty(sysRole.getRoleName())){
      return "角色名称不能为空";
    }
    try{
      roleService.insertSelective(sysRole);
      //操作role-menu data
      SysRoleMenu sysRoleMenu=new SysRoleMenu();
      sysRoleMenu.setRoleId(sysRole.getId());

      if(menus!=null)
        for(String menu:menus){
          sysRoleMenu.setMenuId(menu);
          roleMenuService.insert(sysRoleMenu);
        }
    }catch (MyException e){
      log.error("[RoleController]{addRole} -> error!",e);
    }
    return "保存成功";
  }

  @GetMapping(value = "updateRole")
  public String updateRole(String id, Model model, boolean detail){
    if (StringUtils.isNotEmpty(id)) {
      SysRole role = roleService.selectByPrimaryKey(id);
      model.addAttribute("role", role);
      JSONArray jsonArray=menuService.getTreeUtil(id);
      model.addAttribute("menus",jsonArray.toJSONString());
    }
    model.addAttribute("detail", detail);
    return "system/role/update-role";
  }

  @ApiOperation(value = "/updateRole", httpMethod = "POST", notes = "更新角色")
  @Log(desc = "更新角色",type = Log.LOG_TYPE.UPDATE)
  @PostMapping(value = "updateRole")
  @ResponseBody
  public JsonUtil updateUser(SysRole role, String[] menus) {
    JsonUtil jsonUtil = new JsonUtil();
    jsonUtil.setFlag(false);
    if (role == null) {
      jsonUtil.setMsg("获取数据失败");
      return jsonUtil;
    }
    try {
      SysRole oldRole = roleService.selectByPrimaryKey(role.getId());
      BeanUtils.copyNotNullBean(role, oldRole);
      roleService.updateByPrimaryKeySelective(oldRole);

      SysRoleMenu sysRoleMenu=new SysRoleMenu();
      sysRoleMenu.setRoleId(role.getId());
      List<SysRoleMenu> menuList=roleMenuService.selectByCondition(sysRoleMenu);
      for(SysRoleMenu sysRoleMenu1:menuList){
        roleMenuService.deleteByPrimaryKey(sysRoleMenu1);
      }
      if(menus!=null)
        for(String menu:menus){
          sysRoleMenu.setMenuId(menu);
          roleMenuService.insert(sysRoleMenu);
        }
      jsonUtil.setFlag(true);
      jsonUtil.setMsg("修改成功");
    } catch (MyException e) {
      log.error("[RoleController]{updateUser} -> error!",e);
    }
    return jsonUtil;
  }

  @ApiOperation(value = "/del", httpMethod = "POST", notes = "删除角色")
  @Log(desc = "删除角色",type = Log.LOG_TYPE.DEL)
  @PostMapping(value = "del")
  @ResponseBody
  @RequiresPermissions("role:del")
  public String del(String id) {
    if (StringUtils.isEmpty(id)) {
      return "获取数据失败";
    }
    SysRoleUser sysRoleUser=new SysRoleUser();
    sysRoleUser.setRoleId(id);
    try {
     int count= roleUserService.selectCountByCondition(sysRoleUser);
     if(count>0){
       return "已分配给用户，删除失败";
     }
        roleService.deleteByPrimaryKey(id);
    } catch (MyException e) {
      log.error("[RoleController]{del} -> error!",e);
    }
    return "删除成功";
  }

}
