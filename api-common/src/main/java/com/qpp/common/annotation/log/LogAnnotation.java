package com.qpp.common.annotation.log;

import com.qpp.common.constant.enums.BusinessType;
import com.qpp.common.constant.enums.OperatorType;

import java.lang.annotation.*;


/**
 * @author qipengpai
 * @Title: DataSource
 * @ProjectName misscy
 * @Description: TODO 日志注解
 * @date 10:30 2018/10/22
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

	/**
	 * 模块
	 */
	String title() default "";

	/**
	 * 是否保存请求的参数
	 * @return
	 */
	boolean recordRequestParam() default true;
	/**
	 * 功能
	 */
	BusinessType businessType() default BusinessType.OTHER;
	/**
	 * 操作人类别
	 */
	OperatorType operatorType() default OperatorType.MANAGE;


}
