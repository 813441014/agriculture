package com.qpp.admin.service.system;

import com.alibaba.fastjson.JSONArray;
import com.qpp.admin.entity.system.SysMenu;
import com.qpp.basic.base.BaseService;

import java.util.List;

/**
 * @author qipengpai
 */
public interface MenuService extends BaseService<SysMenu,String> {

  List<SysMenu> getMenuNotSuper();

  @Override
  int insert(SysMenu menu);

  @Override
  SysMenu selectByPrimaryKey(String id);

  List<SysMenu> getMenuChildren(String id);

  JSONArray getMenuJson(String roleId);

  JSONArray getMenuJsonList();

  List<SysMenu> getMenuChildrenAll(String id);

  JSONArray getTreeUtil(String roleId);

  List<SysMenu> getUserMenu(String id);

  JSONArray getMenuJsonByUser(List<SysMenu> menuList);
}
