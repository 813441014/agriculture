package com.qpp.admin.core.aspect;


import com.qpp.admin.config.datasource.DataSourceContextHolder;
import com.qpp.common.annotation.datasource.DataSource;
import com.qpp.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


/**
 * @author qipengpai
 * @Title: DataSourceAspect
 * @ProjectName agriculture
 * @Description: TODO  切换数据源切面
 * @date 10:30 2018/10/22
 */
@Slf4j
@Aspect
@Order(1)
@Component
public class DataSourceAspect {

    @Pointcut("@annotation(com.qpp.common.annotation.datasource.DataSource)")
    public void dsPointCut() {
        throw new UnsupportedOperationException("不支持调用");
    }

    @Around("dsPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();

        Method method = signature.getMethod();

        DataSource dataSource = method.getAnnotation(DataSource.class);

        if (StringUtils.isNotNull(dataSource)) {
            DataSourceContextHolder.setDatabaseType(dataSource.value());
        }
        try {
            return point.proceed();
        } finally {
            // 销毁数据源 在执行方法之后
            DataSourceContextHolder.clearDataSourceType();
        }
    }
}