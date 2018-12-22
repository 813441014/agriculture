package com.qpp.admin.service.system;

import com.qpp.admin.controller.view.ChannelAccountView;
import com.qpp.admin.entity.system.SysRoleUser;
import com.qpp.admin.entity.system.SysUser;
import com.qpp.basic.base.BaseService;
import com.qpp.basic.util.Checkbox;

import java.util.List;

/**
 * @author qipengpai
 */
public interface SysUserService extends BaseService<SysUser, String> {

    SysUser login(String username);

    @Override
    SysUser selectByPrimaryKey(String id);

    /**
     * 分页查询
     */
    List<SysUser> selectListByPage(SysUser sysUser);

    int count();

    /**
     * 新增
     * @param user
     * @return int
     */
    int add(SysUser user);

    /**
     * 删除
     * @param id
     * @return
     */
    int delById(String id);

    int checkUser(String username);


    int updateByPrimaryKey(SysUser sysUser);

    List<SysRoleUser> selectByCondition(SysRoleUser sysRoleUser);

    public List<Checkbox> getUserRoleByJson(String id);

    /**
     * 更新密码
     * @param user
     * @return
     */
    int rePass(SysUser user);

    List<ChannelAccountView> getChannelAccountList(ChannelAccountView channelAccountView);
}
