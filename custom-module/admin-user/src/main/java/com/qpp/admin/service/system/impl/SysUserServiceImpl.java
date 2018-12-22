package com.qpp.admin.service.system.impl;

import com.qpp.admin.controller.view.ChannelAccountView;
import com.qpp.admin.entity.system.SysRole;
import com.qpp.admin.entity.system.SysRoleUser;
import com.qpp.admin.entity.system.SysUser;
import com.qpp.admin.mapper.system.DataChildMapper;
import com.qpp.admin.mapper.system.SysRoleUserMapper;
import com.qpp.admin.mapper.system.SysUserMapper;
import com.qpp.admin.service.system.RoleService;
import com.qpp.admin.service.system.SysUserService;
import com.qpp.basic.base.BaseMapper;
import com.qpp.basic.base.impl.BaseServiceImpl;
import com.qpp.basic.util.Checkbox;
import com.qpp.basic.util.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qipengpai
 */
@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser,String> implements SysUserService {

  @Autowired
  private SysUserMapper sysUserMapper;

  @Autowired
  private SysRoleUserMapper sysRoleUserMapper;
  @Autowired
  private  RoleService roleService;
  @Autowired
  private DataChildMapper dataChildMapper;

  @Override
  public BaseMapper<SysUser, String> getMappser() {
    return sysUserMapper;
  }

  @Override
  public SysUser login(String username) {
    return sysUserMapper.login(username);
  }


  @Override
  public int deleteByPrimaryKey(String id) {
    return sysUserMapper.deleteByPrimaryKey(id);
  }

  @Override
  public int insert(SysUser record) {
    return sysUserMapper.insert(record);
  }

  @Override
  public int insertSelective(SysUser record) {

    String pwd= Md5Util.getMD5(record.getPassword().trim(),record.getUsername().trim());
    record.setPassword(pwd);
    return super.insertSelective(record);
  }

  @Override
  public SysUser selectByPrimaryKey(String id) {
    return sysUserMapper.selectByPrimaryKey(id);
  }

  @Override
  public int updateByPrimaryKeySelective(SysUser record) {
    return super.updateByPrimaryKeySelective(record);
  }

  @Override
  public int updateByPrimaryKey(SysUser record) {
    return sysUserMapper.updateByPrimaryKey(record);
  }

  @Override
  public List<SysRoleUser> selectByCondition(SysRoleUser sysRoleUser) {
    return sysRoleUserMapper.selectByCondition(sysRoleUser);
  }

  /**
   * 分页查询
   * @param
   * @return
   */
  @Override
  public List<SysUser> selectListByPage(SysUser sysUser) {
    return sysUserMapper.selectListByPage(sysUser);
  }

  @Override
  public int count() {
    return sysUserMapper.count();
  }

  @Override
  public int add(SysUser user) {
    //密码加密
  String pwd= Md5Util.getMD5(user.getPassword().trim(),user.getUsername().trim());
  user.setPassword(pwd);
    return sysUserMapper.add(user);
  }

  @Override
  public int delById(String id) {
    return sysUserMapper.delById(id);

  }

  @Override
  public int checkUser(String username) {
    return sysUserMapper.checkUser(username);
  }

  @Override
  public List<Checkbox> getUserRoleByJson(String id){
    List<SysRole> roleList=roleService.selectListByPage(new SysRole());
    SysRoleUser sysRoleUser =new SysRoleUser();
    sysRoleUser.setUserId(id);
    List<SysRoleUser>  kList= selectByCondition(sysRoleUser);
    System.out.println(kList.size());
    List<Checkbox> checkboxList=new ArrayList<>();
    Checkbox checkbox=null;
    for(SysRole sysRole:roleList){
      checkbox=new Checkbox();
      checkbox.setId(sysRole.getId());
      checkbox.setName(sysRole.getRoleName());
      for(SysRoleUser sysRoleUser1 :kList){
        if(sysRoleUser1.getRoleId().equals(sysRole.getId())){
          checkbox.setCheck(true);
        }
      }
      checkboxList.add(checkbox);
    }
    return checkboxList;
  }

  @Override
  public int rePass(SysUser user) {
    return sysUserMapper.rePass(user);
  }

  @Override
  public List<ChannelAccountView> getChannelAccountList(ChannelAccountView channelAccountView) {
    return this.dataChildMapper.getChannelAccountList(channelAccountView);
  }
}
