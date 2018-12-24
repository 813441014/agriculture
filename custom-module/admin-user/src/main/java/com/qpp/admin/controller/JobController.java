package com.qpp.admin.controller;

import com.qpp.common.annotation.log.Log;
import com.qpp.admin.core.quartz.JobTask;
import com.qpp.admin.entity.system.SysJob;
import com.qpp.admin.service.system.JobService;
import com.qpp.basic.base.BaseController;
import com.qpp.basic.exception.MyException;
import com.qpp.basic.util.JsonUtil;
import com.qpp.common.utils.bean.BeanUtils;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author qipengpai
 *
 * 定时任务 controller
 */
@Controller
@RequestMapping("/job")
public class JobController extends BaseController {

  private static final String UPDATE_FAILD = "获取数据失败";

  @Autowired
  JobService jobService;

  @Autowired
  JobTask jobTask;

  @GetMapping(value = "showJob")
  @RequiresPermissions("job:show")
  public String showUser(Model model) {
    return "/system/job/jobList";
  }

  @GetMapping(value = "showJobList")
  @ResponseBody
  @RequiresPermissions("job:show")
  public String showUser(Model model, SysJob job, String page, String limit) {
    return jobService.show(job,Integer.valueOf(page),Integer.valueOf(limit));
  }

  @GetMapping(value = "showAddJob")
  public String addJob(Model model) {
    return "/system/job/add-job";
  }

  @ApiOperation(value = "/addJob", httpMethod = "POST", notes = "添加任务类")
  @Log(desc = "添加任务" ,type = Log.LOG_TYPE.ADD)
  @PostMapping(value = "addJob")
  @ResponseBody
  public JsonUtil addJob(SysJob job) {
    JsonUtil j=new JsonUtil();
    String msg="保存成功";
    job.setStatus(false);
    try {
      jobService.insertSelective(job);
    }catch (MyException e){
      msg="保存失败";
      j.setFlag(false);
      e.printStackTrace();
    }
    j.setMsg(msg);
    return j;
  }

  @GetMapping(value = "updateJob")
  public String updateJob(String id, Model model, boolean detail) {
    if (StringUtils.isNotEmpty(id)) {
      SysJob job = jobService.selectByPrimaryKey(id);
      model.addAttribute("job", job);
    }
    model.addAttribute("detail", detail);
    return "system/job/update-job";
  }


  @ApiOperation(value = "/updateJob", httpMethod = "POST", notes = "更新任务")
  @Log(desc = "更新任务",type = Log.LOG_TYPE.UPDATE)
  @PostMapping(value = "updateJob")
  @ResponseBody
  public JsonUtil updateJob(SysJob job){
    JsonUtil j=new JsonUtil();
    j.setFlag(false);
    if (job == null) {
      j.setMsg(UPDATE_FAILD);
      return j;
    }
    if(jobTask.checkJob(job)){
      j.setMsg("已经启动任务无法更新,请停止后更新");
      return j;
    }
    try{
      SysJob oldJob=jobService.selectByPrimaryKey(job.getId());
      BeanUtils.copyNotNullBean(job, oldJob);
      jobService.updateByPrimaryKey(oldJob);
      j.setFlag(true);
      j.setMsg("修改成功");
    }catch (MyException e){
      j.setMsg("更新失败");
      e.printStackTrace();
    }
    return j;
  }

  @Log(desc = "删除任务",type = Log.LOG_TYPE.DEL)
  @ApiOperation(value = "/del", httpMethod = "POST", notes = "删除任务")
  @PostMapping(value = "del")
  @ResponseBody
  @RequiresPermissions("job:del")
  public JsonUtil del(String id){
    JsonUtil j=new JsonUtil();
    j.setFlag(false);
    if (StringUtils.isEmpty(id)) {
      j.setMsg(UPDATE_FAILD);
      return j;
    }
    try{
      SysJob job=jobService.selectByPrimaryKey(id);
      boolean flag=jobTask.checkJob(job);
      if((flag&&!job.getStatus())||!flag&&job.getStatus()){
        j.setMsg("您任务表状态和web任务状态不一致,无法删除");
        return j;
      }
      if(flag){
        j.setMsg("该任务处于启动中，无法删除");
        return j;
      }
      jobService.deleteByPrimaryKey(id);
      j.setFlag(true);
      j.setMsg("任务删除成功");
    }catch (MyException e){
      j.setMsg("任务删除异常");
      e.printStackTrace();
    }
    return j;
  }


  @Log(desc = "启动任务")
  @PostMapping(value = "startJob")
  @ResponseBody
  @RequiresPermissions("job:start")
  public JsonUtil startJob(String id){
    JsonUtil j=new JsonUtil();
    String msg=null;
    if(StringUtils.isEmpty(id)){
      j.setMsg(UPDATE_FAILD);
      j.setFlag(false);
      return j;
    }
    try {
      SysJob job = jobService.selectByPrimaryKey(id);
      jobTask.startJob(job);
      job.setStatus(true);
      jobService.updateByPrimaryKey(job);
      msg="启动成功";
    }catch (MyException e){
      e.printStackTrace();
    }
    j.setMsg(msg);
    return j;
  }

  @Log(desc = "停止任务")
  @PostMapping(value = "endJob")
  @ResponseBody
  @RequiresPermissions("job:end")
  public JsonUtil endJob(String id){
    JsonUtil j=new JsonUtil();
    String msg=null;
    if(StringUtils.isEmpty(id)){
      j.setMsg(UPDATE_FAILD);
      j.setFlag(false);
      return j;
    }
    try {
      SysJob job = jobService.selectByPrimaryKey(id);
      jobTask.remove(job);
      job.setStatus(false);
      jobService.updateByPrimaryKey(job);
      msg="停止成功";
    }catch (MyException e){
      e.printStackTrace();
    }
    j.setMsg(msg);
    return j;
  }

}
