package com.qpp.admin.mapper.system;

import com.qpp.admin.entity.system.SysRoleUser;
import com.qpp.basic.base.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleUserMapper extends BaseMapper<SysRoleUser,String> {

    int deleteByPrimaryKey(SysRoleUser key);

    int insert(SysRoleUser record);

    int insertSelective(SysRoleUser record);

    List<SysRoleUser> selectByCondition(SysRoleUser sysRoleUser);

    int selectCountByCondition(SysRoleUser sysRoleUser);

    int deleteByUserId(SysRoleUser key);
}