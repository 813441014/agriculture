package com.qpp.common.annotation.log;

import java.lang.annotation.*;

/**
 * @version 1.0.1
 * @Author qipengpai
 * @ClassName Log
 * @Description //TODO  记录日志
 * @Date 16:37 2018/12/18
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
@Documented
@Inherited
public @interface Log {

    public enum LOG_TYPE {
        ADD, UPDATE, DEL, SELECT, ATHOR
    };

    /** 内容 */
    String desc();

    /** 类型 curd */
    LOG_TYPE type() default LOG_TYPE.ATHOR;
}
