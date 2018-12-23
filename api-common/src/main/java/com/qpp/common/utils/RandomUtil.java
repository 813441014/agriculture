/******************************************************************
 *
 *    Package:     com.company.platform.base.util
 *
 *    Filename:    RandomUtil.java
 *
 *    Description: TODO(用一句话描述该文件做什么)
 *
 *    Copyright:   Copyright (c) 2017
 *
 *    Company:     北京中科博润科技股份有限公司
 *
 *    @author:     zhengjn
 *
 *    @version:    1.0.0
 *
 *    Create at:   2017年3月27日 下午5:33:58
 *
 *    Revision:
 *
 *    2017年3月27日 下午5:33:58
 *        - first revision
 *
 *****************************************************************/
package com.qpp.common.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName: RandomUtil
 * @Description: TODO Random工具类
 * @author zhengjn
 * @date 2018/12/23 4:04:10
 * 
 */
@Slf4j
public class RandomUtil {

	/**
	 * @Fields mapTable : TODO(用于生成数字随机数)
	 */
	private static final char digitTable[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3',
			'4', '5', '6', '7', '8', '9' };
	/**
	 * @Fields mapTable : TODO(用于生成混合随机数)
	 */
	private static final char mixTable[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4',
			'5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p',
			'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

	/** 
	* @Title: getRandomDigit 
	* @Description: TODO(获取存数字随机数) 
	* @param @param digit
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws 
	*/
	public static int getRandomDigit(int digit) {
		String strEnsure = "";
		// 4代表4位验证码,如果要生成更多位的认证码,则加大数值
		for (int i = 0; i < digit; ++i) {
			strEnsure += digitTable[(int) (digitTable.length * Math.random())];
			// 将认证码显示到图象中
		}
		return Integer.valueOf(strEnsure);
	}

	/** 
	* @Title: getRandomMix 
	* @Description: TODO(获取字母数字混合随机码) 
	* @param @param digit
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	public static String getRandomMix(int digit) {
		String strEnsure = "";
		// 4代表4位验证码,如果要生成更多位的认证码,则加大数值
		for (int i = 0; i < digit; ++i) {
			strEnsure += mixTable[(int) (mixTable.length * Math.random())];
			// 将认证码显示到图象中
		}
		return strEnsure;
	}

	/**
	 * @Title: main @Description: TODO(这里用一句话描述这个方法的作用) @param @param args
	 *         设定文件 @return void 返回类型 @throws
	 */
	public static void main(String[] args) {
		log.info(getRandomDigit(8)+"");
		log.info(getRandomMix(8));
	}

}
