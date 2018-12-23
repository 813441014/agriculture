package com.qpp.admin.core.quartz;

import com.qpp.admin.entity.system.SchdulerJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.lang.reflect.Method;



/**
 * @version 1.0.1
 * @Author QuartzManager
 * @ClassName RunJob
 * @Description //TODO  用于任务执行的job最终的执行类
 * @Date 16:37 2018/12/22
 **/
@Slf4j
public class RunJob implements Job{

	@Override
	public void execute(JobExecutionContext ctx) throws JobExecutionException {
		SchdulerJob schdulerJob = (SchdulerJob) ctx.getJobDetail().getJobDataMap().get("schdulerJob");
		String methodName = schdulerJob.getfMethodname();
		String beanName = schdulerJob.getfBeanname();
		try {
			Object bean = Class.forName(beanName);

			/**
			 * TODO 动态执行的方法, 统一成只有一个参数, 即为SchdulerJob
			 */
			Method method = bean.getClass().getMethod(methodName,SchdulerJob.class);
			method.invoke(bean, schdulerJob);
			log.info("动态定时quartz任务执行成功");
		} catch (Exception e) {
			log.error("任务job执行异常, schdulerJob: "+schdulerJob.toString(), e);
		}
	}
}
