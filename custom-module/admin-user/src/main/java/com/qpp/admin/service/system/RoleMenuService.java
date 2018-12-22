package com.qpp.admin.service.system;

import com.qpp.admin.entity.system.SysRoleMenu;
import com.qpp.basic.base.BaseService;

import java.util.List;

/**
 * @author qipengpai
 */
public interface RoleMenuService extends BaseService<SysRoleMenu,String> {

    List<SysRoleMenu> selectByCondition(SysRoleMenu sysRoleMenu);

    int  selectCountByCondition(SysRoleMenu sysRoleMenu);

    int deleteByPrimaryKey(SysRoleMenu sysRoleMenu);
    int deleteByMenuId(String menuId);
}
