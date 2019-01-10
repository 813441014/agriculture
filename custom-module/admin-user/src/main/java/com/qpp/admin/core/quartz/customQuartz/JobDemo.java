package com.qpp.admin.core.quartz.customQuartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;


/**
 * 定时
 */
@Slf4j
public class JobDemo implements Job {

  /**
   * Key used to store the session manager in the job data map for this job.
   */
  public static final String SESSION_MANAGER_KEY = "sessionManager";



  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {

    if (log.isDebugEnabled()) {
      log.debug("Session validation Quartz job complete.");
    }
    log.info("JobDemo：启动任务=======================");
    log.info("JobDemo1：下次执行时间=====" +
            new SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
                    .format(context.getNextFireTime()) + "==============");
  }

}
