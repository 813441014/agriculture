package com.qpp.admin.core.BootListener;

import com.qpp.admin.core.quartz.QuartzManager;
import com.qpp.admin.entity.system.SchdulerJob;
import com.qpp.admin.mapper.system.SchdulerJobMapper;
import com.qpp.admin.service.system.JobService;
import com.qpp.admin.service.system.RoleService;
import com.qpp.admin.service.system.SchdulerJobService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @version 1.0.1
 * @Author qipengpai
 * @ClassName DataSourceJobThread
 * @Description //TODO 启动数据库中已经设定为 启动状态(status:true)的任务 项目启动时init
 * @Date 16:37 2018/12/18
 **/
@Slf4j
@Configuration
public class DataSourceJobThread extends Thread {

  @Autowired
  RoleService roleService;

  @Autowired
  JobService jobService;

  @Autowired
  SchdulerJobMapper schdulerJobMapper;
  @Autowired
  QuartzManager quartzManager;

  @Override
  public void run() {
    try {
      Thread.sleep(1000);
      log.info("动态quartz的初始化, 获取数据库中的任务并添加到quartz管理器中");
      SchdulerJob job = new SchdulerJob();
      List<SchdulerJob> jobList = schdulerJobMapper.selectListByPage(job);
      for(SchdulerJob jobItem : jobList) {
        log.info("---任务["+jobItem.getId()+"]系统 init--开始启动---------");
        quartzManager.addJob(jobItem);
      }
    } catch (Exception e) {
      log.error("动态的quartz任务添加失败", e);
      e.printStackTrace();
    }
  }

/*  public void run() {
    try {
      Thread.sleep(1000);
      log.info("---------线程启动---------");
      JobTask jobTask = SpringUtil.getBean("jobTask");
      SysJob job = new SysJob();
      job.setStatus(true);
      List<SysJob> jobList = jobService.selectListByPage(job);
      //开启任务
      jobList.forEach(jobs -> {
        log.info("---任务["+jobs.getId()+"]系统 init--开始启动---------");
        jobTask.startJob(jobs);
          }
      );
      if(jobList.size()==0){
        log.info("---数据库暂无启动的任务---------");
      }else
      System.out.println("---任务启动完毕---------");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }*/

}
