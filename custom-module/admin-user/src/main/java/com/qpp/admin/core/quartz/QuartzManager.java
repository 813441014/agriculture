package com.qpp.admin.core.quartz;


import com.qpp.admin.entity.system.SchdulerJob;
import com.qpp.basic.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;

/**
 * @version 1.0.1
 * @Author qipengpai
 * @ClassName QuartzManager
 * @Description //TODO  动态添加或删除定时任务
 * @Date 16:37 2018/12/22
 **/
@Slf4j
@Service
public class QuartzManager {
	
	@Autowired
    SchedulerFactoryBean schedulerFactoryBean;
	/**
	 * 添加或更新一个定时任务
	 * @param job
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public  void addJob(SchdulerJob job) throws SchedulerException, ParseException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
    	TriggerKey triggerKey = TriggerKey.triggerKey(job.getfJobname(), job.getfJobgroup());
    	Trigger trigger = scheduler.getTrigger(triggerKey);

    	if(job.getfRuntime() <= System.currentTimeMillis()) {
    		remove(job);
    		log.warn("运行时间不合法, 小于或等于当前时间");
    		return ;
    	}
    	
    	if(trigger==null) {//如果是新任务, 则创建
            JobDetail jobDetail = JobBuilder.newJob(RunJob.class).withIdentity(job.getfJobname(), job.getfJobgroup()).build();
            jobDetail.getJobDataMap().put("schdulerJob", job);
    		trigger = TriggerBuilder.newTrigger().withIdentity(job.getfJobname(), job.getfJobgroup())
    				.withSchedule(CronScheduleBuilder.cronSchedule(new CronExpression(job.getfCronexpression()))).build();
    		scheduler.scheduleJob(jobDetail, trigger);
    	} else {//如果是已存在任务
    		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(new CronExpression(job.getfCronexpression()));
            // 按新的cronExpression表达式重新构建trigger  
            trigger = ((TriggerBuilder<Trigger>)trigger.getTriggerBuilder().withIdentity(triggerKey))
            		.withSchedule(scheduleBuilder).build();
  
            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);  
    	}
	}
	
	/**
	 * 删除一个定时任务
	 * @param job
	 * @throws SchedulerException
	 */
	public  void remove(SchdulerJob job) throws SchedulerException {
		Scheduler scheduler = schedulerFactoryBean.getScheduler();
		JobKey jobKey = JobKey.jobKey(job.getfJobname(), job.getfJobgroup());
		scheduler.deleteJob(jobKey);
	}
	
	/**
	 * 获取指定执行时间的cron表达式
	 * @param date
	 * @param pDays
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static String getCronExpression(Date date, int pDays) {
		Date runDate = DateUtil.getDaysIntervalDate(date, pDays);
		/**
		 * cron格式: 
		 * 41 48 16 9 12 ? 2015
		 * 秒   分   时   天   月   月中的星期  年
		 */
		StringBuilder expression = new StringBuilder();
		expression.append(runDate.getSeconds()+" ");
		expression.append(runDate.getMinutes()+" ");
		expression.append(runDate.getHours()+" ");
		expression.append(runDate.getDate()+" ");
		expression.append((runDate.getMonth()+1)+" ");
		expression.append("? ");
		expression.append(DateUtil.getYear(runDate));
		
		return expression.toString();
	}
	
	/**
	 * 获取指定执行时间的cron表达式
	 * @param time
	 * @param pDays
	 * @return
	 */
	public static String getCronExpression(long time, int pDays) {
		return getCronExpression(new Date(time), pDays);
	}
}
