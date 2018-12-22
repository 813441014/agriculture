package com.qpp.admin.service.system;

import com.qpp.admin.entity.system.SysRole;
import com.qpp.basic.base.BaseService;

import java.util.List;

/**
 * @author qipengpai
 */
public interface RoleService extends BaseService<SysRole,String> {

  @Override
  int deleteByPrimaryKey(String id);

  @Override
  int insert(SysRole record);

  @Override
  int insertSelective(SysRole record);

  @Override
  SysRole selectByPrimaryKey(String id);

  @Override
  int updateByPrimaryKeySelective(SysRole record);

  @Override
  int updateByPrimaryKey(SysRole record);

  List<SysRole> selectListByPage(SysRole sysRole);
}
