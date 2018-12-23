package com.qpp.admin.controller;


import com.alibaba.fastjson.JSONObject;
import com.qpp.admin.controller.dto.SysUserDTO;
import com.qpp.admin.core.annotation.Log;
import com.qpp.admin.core.quartz.JobTask;
import com.qpp.admin.entity.system.SysRole;
import com.qpp.admin.entity.system.SysRoleUser;
import com.qpp.admin.entity.system.SysUser;
import com.qpp.admin.service.system.RoleService;
import com.qpp.admin.service.system.RoleUserService;
import com.qpp.admin.service.system.SysUserService;
import com.qpp.basic.base.BaseController;
import com.qpp.basic.exception.MyException;
import com.qpp.basic.util.Checkbox;
import com.qpp.basic.util.JsonUtil;
import com.qpp.basic.util.Md5Util;
import com.qpp.common.utils.bean.BeanUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author qipengpai
 * 用户管理
 */
//@Api(value="user")
@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    //private static final Logger

    @Autowired
    SysUserService userService;

    @Autowired
    RoleUserService roleUserService;

    @Autowired
    RoleService roleService;

    @Autowired
    JobTask task;

    @GetMapping(value = "mainTest")
    @RequiresPermissions("user:show")
    public String showTest() {
        return "system/user/mainTest";
    }

    @GetMapping(value = "showUser")
    @RequiresPermissions("user:show")
    public String showUser(Model model) {
        return "/system/user/userList";
    }

    @GetMapping(value = "showUserList")
    @ResponseBody
    @RequiresPermissions("user:show")
    public String showUser(Model model, SysUser user, String page, String limit) {
        user.setPhoto("third");
        return userService.show(user, Integer.valueOf(page), Integer.valueOf(limit));
    }

    @GetMapping(value = "showAddUser")
    public String addUser(Model model) {
        List<Checkbox> checkboxList = userService.getUserRoleByJson(null);
        model.addAttribute("boxJson", checkboxList);
        return "/system/user/add-user";
    }

    @ApiOperation(value = "/addUser", httpMethod = "POST", notes = "添加用户")
//    @Log(desc = "添加用户")
    @PostMapping(value = "addUser")
    @ResponseBody
    public String addUser(SysUser user, String[] role) {
        if (user == null) {
            return "获取数据失败";
        }
        if (StringUtils.isBlank(user.getUsername())) {

            return "用户名不能为空";
        }
        if (StringUtils.isBlank(user.getPassword())) {
            return "密码不能为空";
        }
        if (role == null) {
            return "请选择角色";
        }
        int result = userService.checkUser(user.getUsername());
        if (result > 0) {
            return "用户名已存在";
        }
        try {
            user.setEmail("0");
            user.setRealName("0");
            user.setAge(18);
            userService.insertSelective(user);
            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setUserId(user.getId());
            for (String r : role) {
                sysRoleUser.setRoleId(r);
                roleUserService.insertSelective(sysRoleUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "保存成功";

    }

    @GetMapping(value = "updateUser")
    public String updateUser(String id, Model model, boolean detail) {
        if (StringUtils.isNotEmpty(id)) {
            //用户-角色
            List<Checkbox> checkboxList = userService.getUserRoleByJson(id);
            SysUser user = userService.selectByPrimaryKey(id);
            model.addAttribute("user", user);
            model.addAttribute("boxJson", checkboxList);
        }
        model.addAttribute("detail", detail);
        return "system/user/update-user";
    }

    @ApiOperation(value = "/updateUser", httpMethod = "POST", notes = "更新用户")
    @Log(desc = "更新用户", type = Log.LOG_TYPE.UPDATE)
    @PostMapping(value = "updateUser")
    @ResponseBody
    public JsonUtil updateUser(SysUser user, String role[]) {
        JsonUtil jsonUtil = new JsonUtil();
        jsonUtil.setFlag(false);
        if (user == null) {
            jsonUtil.setMsg("获取数据失败");
            return jsonUtil;
        }
        try {
            SysUser oldUser = userService.selectByPrimaryKey(user.getId());
            BeanUtils.copyNotNullBean(user, oldUser);
            userService.updateByPrimaryKeySelective(oldUser);

            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setUserId(oldUser.getId());
            List<SysRoleUser> keyList = userService.selectByCondition(sysRoleUser);
            for (SysRoleUser sysRoleUser1 : keyList) {
                roleUserService.deleteByPrimaryKey(sysRoleUser1);
            }
            if (role != null) {
                for (String r : role) {
                    sysRoleUser.setRoleId(r);
                    roleUserService.insert(sysRoleUser);
                }
            }
            jsonUtil.setFlag(true);
            jsonUtil.setMsg("修改成功");
        } catch (MyException e) {
            e.printStackTrace();
        }
        return jsonUtil;
    }

    @Log(desc = "删除用户", type = Log.LOG_TYPE.DEL)
    @ApiOperation(value = "/del", httpMethod = "POST", notes = "删除用户")
    @PostMapping(value = "/del")
    @ResponseBody
    @RequiresPermissions("user:del")
    public String del(String id) {
        if (StringUtils.isEmpty(id)) {
            return "获取数据失败";
        }

        try {
            SysUser sysUser = userService.selectByPrimaryKey(id);
            if ("admin".equals(sysUser.getUsername())) {
                return "超管无法删除";
            }
            SysRoleUser roleUser = new SysRoleUser();
            roleUser.setUserId(id);
            int count = roleUserService.selectCountByCondition(roleUser);
            if (count > 0) {
                SysRoleUser sysRoleUser = new SysRoleUser();
                sysRoleUser.setUserId(id);
                roleUserService.deleteByUserId(sysRoleUser);
//        return "账户已经绑定角色，无法删除";
            }
//      if (flag) {
//        //逻辑
//        sysUser.setDelFlag(Byte.parseByte("1"));
//        userService.updateByPrimaryKeySelective(sysUser);
//      } else {
            //物理
            userService.delById(id);
//      }
        } catch (MyException e) {
            e.printStackTrace();
        }
        return "删除成功";
    }

    /**
     * 跳转修改密码页面
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping(value = "goRePass")
    public String goRePass(String id, Model model) {
        if (StringUtils.isEmpty(id)) {
            return "获取账户信息失败";
        }
        SysUser user = userService.selectByPrimaryKey(id);
        model.addAttribute("user", user);
        return "/system/user/re-pass";
    }

    /**
     * 修改密码
     *
     * @param id
     * @param pass
     * @param newPwd
     * @return
     */
    @Log(desc = "修改密码", type = Log.LOG_TYPE.UPDATE)
    @PostMapping(value = "rePass")
    @ResponseBody
//  @RequiresPermissions("user:repass")
    public JsonUtil rePass(String id, String pass, String newPwd) {
        boolean flag = StringUtils.isEmpty(id) || StringUtils.isEmpty(pass) || StringUtils.isEmpty(newPwd);
        JsonUtil j = new JsonUtil();
        j.setFlag(false);
        if (flag) {
            j.setMsg("获取数据失败，修改失败");
            j.setFlag(false);
            return j;
        }
        SysUser user = userService.selectByPrimaryKey(id);
        newPwd = Md5Util.getMD5(newPwd, user.getUsername());
        pass = Md5Util.getMD5(pass, user.getUsername());
        if (!pass.equals(user.getPassword())) {
            j.setMsg("原密码不正确");
            j.setFlag(false);
            return j;
        }
        if (newPwd.equals(user.getPassword())) {
            j.setMsg("新密码不能与旧密码相同");
            j.setFlag(false);
            return j;
        }
        user.setPassword(newPwd);
        try {
            userService.rePass(user);
            j.setMsg("修改成功");
            j.setFlag(true);
        } catch (MyException e) {
            e.printStackTrace();
        }
        return j;
    }

    /**
     * 头像上传 目前首先相对路径
     */
    @PostMapping(value = "upload")
    @ResponseBody
    public JsonUtil imgUpload(HttpServletRequest req, @RequestParam("file") MultipartFile file, ModelMap model) {
        JsonUtil j = new JsonUtil();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("hhmmss");

        String fileName = sdf1.format(new Date()) + file.getOriginalFilename();
        String objPath = req.getSession().getServletContext().getRealPath("image/") + sdf.format(new Date()).toString();
        File targetFile1 = new File(objPath, fileName);
        File file2 = new File(objPath);
        if (!file2.exists()) {
            file2.mkdirs();
        }
        if (!targetFile1.exists()) {
            targetFile1.mkdirs();
        }

        try {
            file.transferTo(targetFile1);
        } catch (Exception e) {
            j.setFlag(false);
            j.setMsg("上传失败");
            e.printStackTrace();
        }
        j.setMsg("image/" + sdf.format(new Date()).toString() + "/" + req.getContextPath() + fileName);
        return j;
    }

    /**
     * 验证用户名是否存在
     */
    @GetMapping(value = "checkUser")
    @ResponseBody
    public JsonUtil checkUser(String uname, HttpServletRequest req) {
        JsonUtil j = new JsonUtil();
        j.setFlag(Boolean.FALSE);
        if (StringUtils.isEmpty(uname)) {
            j.setMsg("获取数据失败");
            return j;
        }
        int result = userService.checkUser(uname);
        if (result > 0) {
            j.setMsg("用户名已存在");
            return j;
        }
        j.setFlag(true);
        return j;

    }

    @ApiOperation(value = "/addThirdUser", httpMethod = "POST", notes = "添加第三方渠道用户")
    @Log(desc = "添加第三方渠道用户")
    @PostMapping(value = "addThirdUser")
    @ResponseBody
    public Object addThirdUser(SysUserDTO user) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("success", false);
        if (user == null) {
            jsonObject.put("message", "获取数据失败");
            return jsonObject.toString();
        }
        try {
            int result = userService.checkUser(user.getUsername());
            if (result > 0) {
                jsonObject.put("message", "用户名已存在");
                return jsonObject.toString();
            }
            user.setEmail("bd@nmpocket.com");
            user.setRealName("第三方用户");
            user.setAge(35);
            user.setPhoto("third");
            user.setCreateDate(new Date());
//            byte b = 1;
//            user.setDelFlag(b);
            userService.insertSelective(user);
//            DataChild dataChild = new DataChild();
//            dataChild.setName(user.getChannelName());
//            dataChild.setIdentify(user.getUsername());
//            int count = this.dataService.insertNewDataChild(dataChild);
            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setUserId(user.getId());
            SysRole sysRole = new SysRole();
            sysRole.setRoleName("thirdParty");
            List<SysRole> sysRoleList = this.roleService.selectListByPage(sysRole);
            if (sysRoleList != null && sysRoleList.size() > 0) {
                SysRole resultRole = sysRoleList.get(0);
                sysRoleUser.setRoleId(resultRole.getId());
                roleUserService.insertSelective(sysRoleUser);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        jsonObject.put("success", true);
        return jsonObject.toString();
    }

    @ApiOperation(value = "/updateUserDelFlag", httpMethod = "POST", notes = "更新用户启用状态")
    @Log(desc = "更新用户启用状态", type = Log.LOG_TYPE.UPDATE)
    @PostMapping(value = "updateUserDelFlag")
    @ResponseBody
    public JsonUtil updateUserDelFlag(SysUser user) {
        JsonUtil jsonUtil = new JsonUtil();
        jsonUtil.setFlag(false);
        if (user == null) {
            jsonUtil.setMsg("获取数据失败");
            return jsonUtil;
        }
        try {
            SysUser oldUser = userService.selectByPrimaryKey(user.getId());
            BeanUtils.copyNotNullBean(user, oldUser);
            userService.updateByPrimaryKeySelective(oldUser);
            jsonUtil.setFlag(true);
            byte flag = 1;
            if (user.getDelFlag() == flag)
                jsonUtil.setMsg("账号已禁用");
            else {
                jsonUtil.setMsg("账号已启用");
            }
        } catch (MyException e) {
            e.printStackTrace();
        }
        return jsonUtil;
    }

    @Log(desc = "重置密码", type = Log.LOG_TYPE.UPDATE)
    @PostMapping(value = "retpPass")
    @ResponseBody
    public JsonUtil retpPass(String id) {
        boolean flag = StringUtils.isEmpty(id);
        JsonUtil j = new JsonUtil();
        j.setFlag(false);
        if (flag) {
            j.setMsg("获取数据失败，修改失败");
            j.setFlag(false);
            return j;
        }
        SysUser user = userService.selectByPrimaryKey(id);
        String newPwd = Md5Util.getMD5("123abc", user.getUsername());
        user.setPassword(newPwd);
        try {
            userService.rePass(user);
            j.setMsg("密码重置成功");
            j.setFlag(true);
        } catch (MyException e) {
            e.printStackTrace();
        }
        return j;
    }
}
