package com.qpp.admin.service.log;

import com.qpp.admin.entity.system.SysLog;

import java.util.concurrent.Future;

/**
 * @ClassName LogService
 * @Description TODO 日志
 * @Author qipengpai
 * @Date 2018/12/24 16:41
 * @Version 1.0.1
 */
public interface LogService {

    Future<String> insertLog(SysLog sysLog);
}
