package com.qpp.admin.mapper.system;


import com.qpp.admin.entity.system.SysMenu;
import com.qpp.basic.base.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysMenuMapper extends BaseMapper<SysMenu,String> {

    @Override
    int deleteByPrimaryKey(String id);

    @Override
    int insert(SysMenu record);

    @Override
    int insertSelective(SysMenu record);

    @Override
    SysMenu selectByPrimaryKey(String id);

    @Override
    int updateByPrimaryKeySelective(SysMenu record);

    @Override
    int updateByPrimaryKey(SysMenu record);
        /**获取元节点*/
    List<SysMenu> getMenuNotSuper();

    /**
     * 获取子节点
     * @return
     */
    List<SysMenu> getMenuChildren(String id);

    List<SysMenu> getMenuChildrenAll(String id);

    /**
     * 根据用户获取所有菜单
     * @param id
     * @return
     */
    List<SysMenu> getUserMenu(@Param("id") String id);

}