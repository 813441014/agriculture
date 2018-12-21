package com.qpp.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.qpp.admin.core.annotation.Log;
import com.qpp.admin.entity.system.SysMenu;
import com.qpp.admin.entity.system.SysRoleMenu;
import com.qpp.admin.service.system.MenuService;
import com.qpp.admin.service.system.RoleMenuService;
import com.qpp.basic.base.BaseController;
import com.qpp.basic.exception.MyException;
import com.qpp.basic.util.BeanUtil;
import com.qpp.basic.util.JsonUtil;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author langmingsheng
 * 菜单
 */
@Controller
@RequestMapping(value = "/menu")
public class MenuController extends BaseController {

  @Autowired
  private MenuService menuService;
  @Autowired
  private RoleMenuService roleMenuService;

  /**
   * 展示tree
   * @param model
   * @return
   */
  @ApiOperation(value = "/showMenu", httpMethod = "GET", notes = "展示菜单")
  @Log(desc = "展示菜单",type = Log.LOG_TYPE.SELECT)
  @GetMapping(value = "showMenu")
  @RequiresPermissions("menu:show")
  public String showMenu(Model model){
    JSONArray ja=menuService.getMenuJsonList();
    model.addAttribute("menus", ja.toJSONString());
    return "/system/menu/menuList";
  }

  @GetMapping(value = "showAddMenu")
  public String addMenu(Model model){
    JSONArray ja=menuService.getMenuJsonList();
    model.addAttribute("menus", ja.toJSONString());
    return "/system/menu/add-menu";
  }

  @ApiOperation(value = "/addMenu", httpMethod = "POST", notes = "添加菜单")
  @PostMapping(value = "addMenu")
  @ResponseBody
  public JsonUtil addMenu(SysMenu sysMenu, Model model){
    if(StringUtils.isEmpty(sysMenu.getPId())){
      sysMenu.setPId(null);
    }
    if(StringUtils.isEmpty(sysMenu.getUrl())){
      sysMenu.setUrl(null);
    }
    if(StringUtils.isEmpty(sysMenu.getPermission())){
      sysMenu.setPermission(null);
    }
    JsonUtil jsonUtil=new JsonUtil();
    jsonUtil.setFlag(false);
    if(sysMenu==null){
      jsonUtil.setMsg("获取数据失败");
      return jsonUtil;
    }
    try{
      if(sysMenu.getMenuType()==2){
        sysMenu.setMenuType((byte)0);
      }
      menuService.insertSelective(sysMenu);
      jsonUtil.setMsg("添加成功");
    }catch (MyException e){
      e.printStackTrace();
      jsonUtil.setMsg("添加失败");
    }
    return jsonUtil;
  }
  @ApiOperation(value = "/del", httpMethod = "POST", notes = "删除菜单")
  @Log(desc = "删除菜单",type = Log.LOG_TYPE.DEL)
  @PostMapping(value = "del")
  @ResponseBody
//  @RequiresPermissions("menu:del")
  public String del(String id) {
    if (StringUtils.isEmpty(id)) {
      return "获取数据失败";
    }
    SysRoleMenu sysRoleMenu=new SysRoleMenu();
    sysRoleMenu.setMenuId(id);
    try {
//      int count= roleMenuService.selectCountByCondition(sysRoleMenu);
//      if(count>0){
//        return "已分配给角色，删除失败";
//      }
      roleMenuService.deleteByMenuId(id);
      menuService.deleteByPrimaryKey(id);
    } catch (MyException e) {
      e.printStackTrace();
    }
    return "删除成功";
  }

  /**
   * 跳转更新页面
   * @param model
   * @param id
   * @return
   */
  @GetMapping(value = "showUpdateMenu")
  public String showUpdateMenu(Model model, String id, boolean detail){
    SysMenu sysMenu = menuService.selectByPrimaryKey(id);
    JSONArray ja=menuService.getMenuJsonList();
    model.addAttribute("menus", ja.toJSONString());
    model.addAttribute("sysMenu", sysMenu);
    if(null!=sysMenu.getPId()){
      SysMenu pSysMenu=menuService.selectByPrimaryKey(sysMenu.getPId());
      model.addAttribute("pName", pSysMenu.getName());
    }
    model.addAttribute("detail", detail);
    return "/system/menu/update-menu";
  }


  @Log(desc = "更新菜单",type = Log.LOG_TYPE.ADD)
  @PostMapping(value = "updateMenu")
  @ResponseBody
  public JsonUtil updateMenu(SysMenu sysMenu){
    SysMenu oldMenu = menuService.selectByPrimaryKey(sysMenu.getId());
    BeanUtil.copyNotNullBean(sysMenu,oldMenu);
    menuService.updateByPrimaryKeySelective(oldMenu);
    return JsonUtil.sucess("保存成功");
  }

}
