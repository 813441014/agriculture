package com.qpp.admin.mapper.system;


import com.qpp.admin.entity.system.SysJob;
import com.qpp.basic.base.BaseMapper;

public interface SysJobMapper extends BaseMapper<SysJob,String> {
    int deleteByPrimaryKey(String id);

    int insert(SysJob record);

    int insertSelective(SysJob record);

    SysJob selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysJob record);

    int updateByPrimaryKey(SysJob record);
}