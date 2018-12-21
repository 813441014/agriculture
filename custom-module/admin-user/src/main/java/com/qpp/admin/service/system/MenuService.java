package com.qpp.admin.service.system;

import com.alibaba.fastjson.JSONArray;
import com.qpp.admin.entity.system.SysMenu;
import com.qpp.basic.base.BaseService;

import java.util.List;

/**
 * @author langmingsheng
 */
public interface MenuService extends BaseService<SysMenu,String> {

  List<SysMenu> getMenuNotSuper();

  @Override
  int insert(SysMenu menu);

  @Override
  SysMenu selectByPrimaryKey(String id);

  List<SysMenu> getMenuChildren(String id);

  public JSONArray getMenuJson(String roleId);

  public JSONArray getMenuJsonList();

  List<SysMenu> getMenuChildrenAll(String id);

  public JSONArray getTreeUtil(String roleId);

  List<SysMenu> getUserMenu(String id);

  public JSONArray getMenuJsonByUser(List<SysMenu> menuList);
}
