package com.qpp.admin.mapper.system;


import com.qpp.admin.entity.system.SysRole;
import com.qpp.basic.base.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleMapper  extends BaseMapper<SysRole,String> {

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

    @Override
    List<SysRole> selectListByPage(SysRole sysRole);
}