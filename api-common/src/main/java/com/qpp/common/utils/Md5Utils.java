package com.qpp.common.utils;

import lombok.extern.slf4j.Slf4j;

import javax.annotation.Nonnull;
import java.security.MessageDigest;

/**
 * @author qipengpai
 * @Title: Md5Utils
 * @ProjectName misscy
 * @Description: TODO  Md5加密方法
 * @date 12:30 2018/10/22
 */
@Slf4j
public class Md5Utils {

    private Md5Utils(){}

    private static final String UTF_8= "UTF-8";

    private static byte[] md5(String s) {
        MessageDigest algorithm;
        try {
            algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(s.getBytes(UTF_8));
            byte[] messageDigest = algorithm.digest();
            return messageDigest;
        } catch (Exception e) {
            log.error("MD5 Error...", e);
        }
        return IpUtils.NULL_BYTEARRAY;
    }

    private static final String toHex(byte hash[]) {
        if (hash == null) {
            return null;
        }
        StringBuilder buf = new StringBuilder(hash.length * 2);
        int i;

        for (i = 0; i < hash.length; i++) {
            if ((hash[i] & 0xff) < 0x10) {
                buf.append("0");
            }
            buf.append(Long.toString(hash[i] & 0xff, 16));
        }
        return buf.toString();
    }

    public static String hash(@Nonnull String s) {
        try {
            return new String(toHex(md5(s)).getBytes(UTF_8), UTF_8);
        }
        catch (Exception e) {
            log.error("not supported charset...{}", e);
            return s;
        }
    }
}
