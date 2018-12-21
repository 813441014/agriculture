package com.qpp.common.utils;

import com.qpp.common.config.Global;
import com.qpp.common.json.JSON;
import com.qpp.common.json.JSONObject;
import com.qpp.common.utils.http.HttpUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName AddressUtils
 * @Description TODO 获取地址类
 * @Author qipengpai
 * @Date 2018/10/23 11:49
 * @Version 1.0.1
 */
@Slf4j
public class AddressUtils {

    public static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";

    public static String getRealAddressByIP(String ip) {
        String address = "XX XX";

        // 内网不查询
        if (IpUtils.internalIp(ip)) {
            return "内网IP";
        }
        if (Global.isAddressEnabled()) {
            String rspStr = HttpUtils.sendPost(IP_URL, "ip=" + ip);
            if (StringUtils.isEmpty(rspStr))
            {
                log.error("获取地理位置异常 {}", ip);
                return address;
            }
            JSONObject obj;
            try
            {
                obj = JSON.unmarshal(rspStr, JSONObject.class);
                JSONObject data = obj.getObj("data");
                String region = data.getStr("region");
                String city = data.getStr("city");
                address = region + " " + city;
            }
            catch (Exception e)
            {
                log.error("获取地理位置异常 {}", ip);
            }
        }
        return address;
    }
}
