package com.qpp.common.constant.enums;

/**
 * @ClassName ResponseConstants
 * @Author qipengpai
 * @Description //TODO 响应代码枚举
 * @Date 10:51 2018/10/31
 */
public enum ResponseConstants {

    /**
     * @Fields REQUEST_SUCCESS : TODO(请求调用成功)
     */
    REQUEST_SUCCESS("0000", "请求成功"),

    /**
     * @Fields REQUEST_FAIL : TODO(请求调用失败)
     */
    REQUEST_FAIL("9999", "请求失败");

    private String retCode;

    private String retMsg;

    private ResponseConstants(String retCode, String retMsg) {
        this.retCode = retCode;
        this.retMsg = retMsg;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    public static String getRetMsg(String retCode) {
        ResponseConstants[] responseConstants = ResponseConstants.values();
        for (ResponseConstants ret : responseConstants) {
            if (ret.getRetCode().equals(retCode)) {
                return ret.getRetMsg();
            }
        }
        return "";
    }
}
