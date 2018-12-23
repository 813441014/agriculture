package com.qpp.admin.core.annotation;

import com.alibaba.fastjson.JSON;
import com.qpp.admin.core.shiro.ShiroUtil;
import com.qpp.admin.entity.system.SysLog;
import com.qpp.admin.mapper.system.SysLogMapper;
import com.qpp.basic.base.bean.CurrentUser;
import com.qpp.common.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @version 1.0.1
 * @Author qipengpai
 * @ClassName LogAspect
 * @Description //TODO 为增删改添加监控
 * @Date 16:37 2018/12/18
 **/
@Slf4j
@Aspect
@Component
public class LogAspect {

    @Autowired
    private SysLogMapper logMapper;

    @Pointcut("@annotation(com.qpp.admin.core.annotation.Log)")
    private void pointcut() {

    }

    @After("pointcut()")
    public void insertLogSuccess(JoinPoint jp) {
        addLog(jp, getDesc(jp));
    }

    private void addLog(JoinPoint jp, String text) {
        Log.LOG_TYPE type = getType(jp);
        SysLog sysLog = new SysLog();
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        //一些系统监控
        if (requestAttributes != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String ip = IpUtils.getIpAddr(request);
            sysLog.setIp(ip);
        }
        sysLog.setCreateTime(new Date());
        sysLog.setType(type.toString());
        sysLog.setText(text);

        Object[] obj = jp.getArgs();
        StringBuffer buffer = new StringBuffer();
        if (obj != null) {
            for (int i = 0; i < obj.length; i++) {
                buffer.append("[参数" + (i + 1) + ":");
                buffer.append(JSON.toJSONString(obj[i]));
                buffer.append("]");
            }
        }
        sysLog.setParam(buffer.toString());
        try {
            CurrentUser currentUser = ShiroUtil.getCurrentUse();
            sysLog.setUserName(currentUser.getUsername());
        } catch (UnavailableSecurityManagerException e) {
            log.error("[LogAspect]{addLog} -> error!",e);
        }
        logMapper.insert(sysLog);
    }

    /**
     * @Author qipengpai
     * @Description //TODO 记录异常
     * @Date 17:08 2018/12/22
     * @Param [joinPoint, e]
     * @return void
     * @throws 
     **/
    @AfterThrowing(value = "pointcut()", throwing = "e")
    public void afterException(JoinPoint joinPoint, Exception e) {
        log.error("-----------afterException:" + e.getMessage());
        addLog(joinPoint, getDesc(joinPoint) + e.getMessage());
    }

    private String getDesc(JoinPoint joinPoint) {
        MethodSignature methodName = (MethodSignature) joinPoint.getSignature();
        Method method = methodName.getMethod();
        return method.getAnnotation(Log.class).desc();
    }

    private Log.LOG_TYPE getType(JoinPoint joinPoint) {
        MethodSignature methodName = (MethodSignature) joinPoint.getSignature();
        Method method = methodName.getMethod();
        return method.getAnnotation(Log.class).type();
    }
}
