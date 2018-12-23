package com.qpp.common.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderID {

	/**
     * getPK,获得数据库使用的一个long型唯一主键
     * 16位，同一微秒内3000个不会重复
     * @return long
     */
    private static long[] ls = new long[3000];
    private static int li = 0;
    public static synchronized String getrimaryKey() {
        long lo = getPk();
        for (int i = 0; i < 3000; i++) {
            long lt = ls[i];
            if (lt == lo) {
            	String loStr = getrimaryKey();
                lo = Long.valueOf(loStr);
                break;
            }
        }
        ls[li] = lo;
        li++;
        if (li == 3000)
        {
            li = 0;
        }
        return String.valueOf(lo);
    }

    private static long getPk(){
        String a = (String.valueOf(System.currentTimeMillis())).substring(3, 13);
        String d = (String.valueOf(Math.random())).substring(2, 8);
        return Long.parseLong(a + d);
    }
    
    public static void main(String[] args){
    	String des = "2";
		String[] info = des.split("&");
		String detailDes = "";
		String thirdCode = "";
		for (int i = 0; i < info.length; i++) {
			String desInfo = info[i].replace(" ", "");
			if(i==0){
				detailDes=desInfo;
			}else if(i==1){
				thirdCode=desInfo;
			}
		}
		log.info(detailDes);
        log.info(thirdCode);
    }
	
}
