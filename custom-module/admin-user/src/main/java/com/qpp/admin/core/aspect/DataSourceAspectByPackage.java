package com.qpp.admin.core.aspect;

import com.qpp.admin.config.datasource.DataSourceContextHolder;
import com.qpp.common.constant.enums.DataSourceType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @ClassName DataSourceAspectByPackage
 * @Description TODO 数据源动态切换，aop实现 在调用相关子包下的mapper时自动执行 目前只支持非继承BaseMapper类
 * @Author qipengpai
 * @Date 2018/12/24 13:20
 * @Version 1.0.1
 */
@Slf4j
@Aspect
@Component
public class DataSourceAspectByPackage {

    @Pointcut("execution(* com.qpp.admin.mapper..*.*(..))")
    public void aspect() {
        throw new UnsupportedOperationException("不支持调用");
    }


    @Around("aspect()")
    public Object before(ProceedingJoinPoint joinPoint)throws Throwable {
        String packName = joinPoint.getSignature().getDeclaringType().getPackage().getName();
        log.info("----------->包名 ："+packName);
        if (packName.contains("log")) {
            DataSourceContextHolder.setDatabaseType(DataSourceType.LOG);
            log.info("-----------> 切换数据源为：log");
        }
        try {
            return joinPoint.proceed();
        } finally {
            // 销毁数据源 在执行方法之后
            DataSourceContextHolder.clearDataSourceType();
            log.info("-----------> 销毁数据源");
        }

    }

}
