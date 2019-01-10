package com.qpp.admin.core.threadPool;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName LogAsyncPool
 * @Description TODO 日志线程池设置
 * @Author qipengpai
 * @Date 2018/12/24 16:28
 * @Version 1.0.1
 */
@Configuration
public class LogAsyncPool implements WebMvcConfigurer {


    /**
     * @Author qipengpai
     * @Description //TODO 日志线程池设置
     * @Date 2018/12/24 16:32
     * @Param [] 
     * @return org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
     * @throws 
     **/
    @Bean(name = "logAsyncPoolExecutor")
    public ThreadPoolTaskExecutor getLogAsyncThreadPoolExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(10);
        taskExecutor.setMaxPoolSize(100);
        taskExecutor.setQueueCapacity(20);
        taskExecutor.setKeepAliveSeconds(200);
        taskExecutor.setThreadNamePrefix("qpp-log-");
        // 线程池对拒绝任务（无线程可用）的处理策略，目前只支持AbortPolicy、CallerRunsPolicy；默认为后者
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //调度器shutdown被调用时等待当前被调度的任务完成
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        //等待时长
        taskExecutor.setAwaitTerminationSeconds(60);
        taskExecutor.initialize();
        return taskExecutor;
    }
}
