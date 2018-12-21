package com.qpp.admin.service.system.impl;

import com.qpp.admin.entity.system.SysRole;
import com.qpp.admin.mapper.system.SysRoleMapper;
import com.qpp.admin.service.system.RoleService;
import com.qpp.basic.base.BaseMapper;
import com.qpp.basic.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author langmingsheng
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<SysRole,String> implements RoleService {

  @Autowired
  private SysRoleMapper roleMapper;

  @Override
  public BaseMapper<SysRole, String> getMappser() {
    return roleMapper;
  }

  @Override
  public int deleteByPrimaryKey(String id) {
    return roleMapper.deleteByPrimaryKey(id);
  }

  @Override
  public int insert(SysRole record) {
    record=super.addValue(record,true);
    return roleMapper.insert(record);
  }

 /* @Override
  public int insertSelective(SysRole record) {
    return roleMapper.insertSelective(record);
  }*/

  @Override
  public SysRole selectByPrimaryKey(String id) {
    return roleMapper.selectByPrimaryKey(id);
  }

  @Override
  public int updateByPrimaryKeySelective(SysRole record) {
    return roleMapper.updateByPrimaryKeySelective(record);
  }

  @Override
  public int updateByPrimaryKey(SysRole record) {
    return roleMapper.updateByPrimaryKey(record);
  }

  @Override
  public List<SysRole> selectListByPage(SysRole sysRole) {
    return roleMapper.selectListByPage(sysRole);
  }
}
