package com.qpp.common.utils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
/**
 * @ClassName: GzippedInputStreamWrapper
 * @Description: TODO map
 * @author qipengpai
 * @date 2017/10/8 3:56:14
 */
@Slf4j
public class GetAddressByBaiduApi {

    private GetAddressByBaiduApi(){}

    /**
     * @Author qipengpai
     * @Description //TODO 根据经纬度获取城市
     * @Date 2018/12/23 22:15
     * @Param [url]
     * @return com.alibaba.fastjson.JSONObject
     * @throws
     **/
	public static JSONObject loadJson (String url) {
        StringBuilder json = new StringBuilder();                  
        try {  
            URL urlObject = new URL(url);  
            URLConnection uc = urlObject.openConnection();  
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream(),"utf-8"));  
            String inputLine = null;  
            while ( (inputLine = in.readLine()) != null) {  
                json.append(inputLine);  
            }  
            in.close();  
        } catch (MalformedURLException e) {
            log.error("[GetAddressByBaiduApi]{loadJson} -> error!",e);
        } catch (IOException e) {
            log.error("[GetAddressByBaiduApi]{loadJson} -> error!",e);
        }  
        return JSONObject.parseObject(json.toString());
    }
	
	 public static void main(String[] args) throws IOException, JSONException {
		String url = "http://api.map.baidu.com/geocoder?location=44.6906623414,124.8110859929&output=json";
	    log.info(loadJson(url).get("status")+"");
        log.info(JSONObject.parseObject(JSONObject.parseObject(loadJson(url).getString("result")).getString("addressComponent")).getString("province"));
	  }
}
