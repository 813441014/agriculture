package com.qpp.admin.service.system;

import com.qpp.admin.entity.system.SysRoleUser;
import com.qpp.basic.base.BaseService;

import java.util.List;

/**
 * @author langmingsheng
 */
public interface RoleUserService  extends BaseService<SysRoleUser,String> {

  int deleteByPrimaryKey(SysRoleUser sysRoleUser);

  int insert(SysRoleUser sysRoleUser);

  int selectCountByCondition(SysRoleUser sysRoleUser);

  List<SysRoleUser> selectByCondition(SysRoleUser sysRoleUser);

  int deleteByUserId(SysRoleUser sysRoleUser);
}
