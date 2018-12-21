package com.qpp.admin.mapper.system;



import com.qpp.admin.entity.system.SysRoleMenu;
import com.qpp.basic.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRoleMenuMapper  extends BaseMapper<SysRoleMenu,String> {

    int deleteByPrimaryKey(SysRoleMenu key);

    int insert(SysRoleMenu record);

    int insertSelective(SysRoleMenu record);

    List<SysRoleMenu> selectByCondition(SysRoleMenu sysRoleMenu);

   int  selectCountByCondition(SysRoleMenu sysRoleMenu);
   int deleteByMenuId(@Param("menuId") String menuId);
}