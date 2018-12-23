package com.qpp.admin.core.quartz;

import com.qpp.admin.core.annotation.Log;
import com.qpp.admin.entity.system.SysJob;
import com.qpp.basic.exception.MyException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;


/**
 * @version 1.0.1
 * @Author qipengpai
 * @ClassName JobTask
 * @Description //TODO  任务名称 默认为 SysJob 类 id 定时任务类 增删改 可参考api：http://www.quartz-scheduler.org/api/2.2.1/
 * @Date 16:37 2018/12/22
 **/
@Slf4j
@Service
public class JobTask {

  @Autowired
  SchedulerFactoryBean schedulerFactoryBean;
  private static final String TASK = "---任务[";

  /**
   * true 存在 false 不存在
   * @param
   * @return
   */
  public boolean checkJob(SysJob job){
    Scheduler scheduler = schedulerFactoryBean.getScheduler();
    TriggerKey triggerKey = TriggerKey.triggerKey(job.getId(), Scheduler.DEFAULT_GROUP);
    try {
      if(scheduler.checkExists(triggerKey)){
        return true;
      }
    } catch (SchedulerException e) {
      log.error("[JobTask]{checkJob} -> error!",e);
    }
    return false;
  }

  /**
   * 开启
   */
  @Log(desc = "开启定时任务")
  public boolean startJob(SysJob job) {
    Scheduler scheduler = schedulerFactoryBean.getScheduler();
    try {
      Class clazz = Class.forName(job.getClazzPath());
      JobDetail jobDetail = JobBuilder.newJob(clazz).build();
      // 触发器
      TriggerKey triggerKey = TriggerKey.triggerKey(job.getId(), Scheduler.DEFAULT_GROUP);
      CronTrigger trigger = TriggerBuilder.newTrigger()
          .withIdentity(triggerKey)
          .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron())).build();
      scheduler.scheduleJob(jobDetail, trigger);
      // 启动
      if (!scheduler.isShutdown()) {
        scheduler.start();
        log.info(TASK + triggerKey.getName() + "]启动成功-------");
        return true;
      }else{
        log.info(TASK + triggerKey.getName() + "]已经运行，请勿再次启动-------");
      }
    } catch (Exception e) {
      throw new MyException("开启定时任务异常");
    }
    return false;
  }

  /**
   * 更新
   */
  @Log(desc = "更新定时任务", type = Log.LOG_TYPE.UPDATE)
  public boolean updateJob(SysJob job) {
    Scheduler scheduler = schedulerFactoryBean.getScheduler();
    String createTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");

    TriggerKey triggerKey = TriggerKey.triggerKey(job.getId(), Scheduler.DEFAULT_GROUP);
    try {
      if (scheduler.checkExists(triggerKey)) {
        return false;
      }

      JobKey jobKey = JobKey.jobKey(job.getId(), Scheduler.DEFAULT_GROUP);

      CronScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule(job.getCron())
          .withMisfireHandlingInstructionDoNothing();
      CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
          .withDescription(createTime).withSchedule(schedBuilder).build();
      JobDetail jobDetail = scheduler.getJobDetail(jobKey);
      HashSet<Trigger> triggerSet = new HashSet<>();
      triggerSet.add(trigger);
      scheduler.scheduleJob(jobDetail, triggerSet, true);
      log.info(TASK+triggerKey.getName()+"]更新成功-------");
      return true;
    } catch (SchedulerException e) {
      log.error("[JobTask]{updateJob} -> error!",e);
    }
    return false;
  }

  /**
   * 删除
   */
  @Log(desc = "删除定时任务", type = Log.LOG_TYPE.DEL)
  public boolean remove(SysJob job) {
    Scheduler scheduler = schedulerFactoryBean.getScheduler();
    TriggerKey triggerKey = TriggerKey.triggerKey(job.getId(), Scheduler.DEFAULT_GROUP);
    try {
      if (checkJob(job)) {
        scheduler.pauseTrigger(triggerKey);
        scheduler.unscheduleJob(triggerKey);
        scheduler.deleteJob(JobKey.jobKey(job.getId(), Scheduler.DEFAULT_GROUP));
        log.info(TASK + triggerKey.getName() + "]删除成功-------");
        return true;
      }
    } catch (SchedulerException e) {
      log.error("[JobTask]{remove} -> error!",e);
    }
    return false;
  }
}
