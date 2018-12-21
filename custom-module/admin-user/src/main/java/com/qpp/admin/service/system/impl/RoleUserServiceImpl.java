package com.qpp.admin.service.system.impl;

import com.qpp.admin.entity.system.SysRoleUser;
import com.qpp.admin.mapper.system.SysRoleUserMapper;
import com.qpp.admin.service.system.RoleUserService;
import com.qpp.basic.base.BaseMapper;
import com.qpp.basic.base.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author langmingsheng
 */
@Service
public class RoleUserServiceImpl extends BaseServiceImpl<SysRoleUser,String> implements
        RoleUserService {

  @Autowired
  private SysRoleUserMapper sysRoleUserMapper;

  @Override
  public BaseMapper<SysRoleUser, String> getMappser() {
    return sysRoleUserMapper;
  }

  @Override
  public int deleteByPrimaryKey(SysRoleUser sysRoleUser) {
    return sysRoleUserMapper.deleteByPrimaryKey(sysRoleUser);
  }

  @Override
  public int selectCountByCondition(SysRoleUser sysRoleUser) {
    return sysRoleUserMapper.selectCountByCondition(sysRoleUser);
  }

  @Override
  public List<SysRoleUser> selectByCondition(SysRoleUser sysRoleUser) {
    return sysRoleUserMapper.selectByCondition(sysRoleUser);
  }

  @Override
  public int deleteByUserId(SysRoleUser sysRoleUser){
    return sysRoleUserMapper.deleteByUserId(sysRoleUser);
  }
}
