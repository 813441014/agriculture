package com.qpp.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public final class IPInfoUtil {
	
	public enum GeographyService {
		
		SinaServiuce("http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json", "ip"),
		
		TaobaoService("http://ip.taobao.com/service/getIpInfo.php", "ip");
		
		private String http;
		
		private String param;
		
		private GeographyService(String http, String param) {  
			this.http = http;  
			this.param = param;  
		}

		public String getHttp() {
			return http;
		}

		public void setHttp(String http) {
			this.http = http;
		}

		public String getParam() {
			return param;
		}

		public void setParam(String param) {
			this.param = param;
		}

	}
	
	public static String getAddressByIP(String iP, GeographyService service) {
		try {
	    	URL url = new URL( service.getHttp() + "?" + service.getParam() + "=" + iP);
	        URLConnection conn = url.openConnection(); 
	        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GBK")); 
	        String line = null; 
	        StringBuffer result = new StringBuffer(); 
	        while((line = reader.readLine()) != null) {
	        	result.append(line); 
	        }
	        reader.close(); 
	        
	        switch(service) {
	        	case SinaServiuce : {
	        		JSONObject object = JSON.parseObject(result.substring(result.indexOf("{"), result.lastIndexOf("}") + 1));
	        		return object.getString("country") + " " + object.getString("province") + " " + object.getString("city");
	        	}
	        	case TaobaoService : {
	        		JSONObject object = JSON.parseObject(result.toString());
	        		object = object.getJSONObject("data");
	        		return object.getString("country") + " " + object.getString("region") + " " + object.getString("city");
	        	}
	        	default : return "";
	        }
		} catch(Exception e) { 
			e.printStackTrace();
	        return "获取位置信息错误"; 
		}
	}
	
	public static void main(String[] args) {
    	System.out.println(getAddressByIP("202.96.75.68", GeographyService.TaobaoService));
    }

}
