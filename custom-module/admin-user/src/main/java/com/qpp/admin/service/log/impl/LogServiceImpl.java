package com.qpp.admin.service.log.impl;

import com.qpp.admin.entity.system.SysLog;
import com.qpp.admin.mapper.log.SysLogMapper;
import com.qpp.admin.service.log.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * @ClassName LogServiceImpl
 * @Description TODO 日志
 * @Author qipengpai
 * @Date 2018/12/24 16:42
 * @Version 1.0.1
 */
@Slf4j
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    /**
     * @Author qipengpai
     * @Description //TODO 异步记录日志
     * @Date 2018/12/24 16:45
     * @Param [sysLog]
     * @return int
     * @throws
     **/
    @Override
    @Async("logAsyncPoolExecutor")
    public Future<String> insertLog(SysLog sysLog) {
        return sysLogMapper.insertSelective(sysLog) > 0 ?new AsyncResult<>("成功"):new AsyncResult<>("失败") ;
    }
}
