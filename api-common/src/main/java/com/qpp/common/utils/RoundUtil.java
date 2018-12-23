package com.qpp.common.utils;

import java.math.BigDecimal;

public class RoundUtil {
	/**
	 * 根据全局配置获取舍入方式
	 * @param round
	 * @return
	 */
	public static int getRound(String round) {
		if("ROUND_CEILING".equals(round)) {
			return BigDecimal.ROUND_CEILING;
		} else if("ROUND_DOWN".equals(round)) {
			return BigDecimal.ROUND_DOWN;
		} else if("ROUND_FLOOR".equals(round)) {
			return BigDecimal.ROUND_FLOOR;
		} else if("ROUND_HALF_DOWN".equals(round)) {
			return BigDecimal.ROUND_HALF_DOWN;
		} else if("ROUND_HALF_EVEN".equals(round)) {
			return BigDecimal.ROUND_HALF_EVEN;
		} else if("ROUND_HALF_UP".equals(round)) {
			return BigDecimal.ROUND_HALF_UP;
		} else if("ROUND_UNNECESSARY".equals(round)) {
			return BigDecimal.ROUND_UNNECESSARY;
		} else if("ROUND_UP".equals(round)) {
			return BigDecimal.ROUND_UP;
		} else {
			return BigDecimal.ROUND_HALF_EVEN; 
		}
	}
}
