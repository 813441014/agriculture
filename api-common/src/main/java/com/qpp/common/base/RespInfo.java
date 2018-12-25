package com.qpp.common.base;

import com.qpp.common.constant.enums.ResponseConstants;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

/**
 * @ClassName RespInfo
 * @Author qipengpai
 * @Description //TODO 普通结果返回值
 * @Date 10:47 2018/10/31
 */
@Data
@ToString
public class RespInfo implements Serializable {

    /**
     * @Fields responseCode : TODO(响应状态码)
     */
    private String responseCode;

    /**
     * @Fields responseMessage : TODO(响应码描述)
     */
    private String responseMessage;

    /**
     * @Fields beans : TODO(结果集)
     */
    private Object resultSet;

    /**
     * @Fields beans : TODO(参数)
     */
    private Map<String,Object> queryCondition;


    //成功返回
    public RespInfo(Object resultSet,String responseMessage) {
        this.resultSet=resultSet;
        this.responseCode = ResponseConstants.REQUEST_SUCCESS.getRetCode();
        this.responseMessage = responseMessage;
    }

    public RespInfo(String responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }


    public RespInfo(Object responseCode,String responseMessage, Object resultSet) {
        this.resultSet=resultSet;
        this.responseCode = ResponseConstants.REQUEST_SUCCESS.getRetCode();
        this.responseMessage = responseMessage;
    }

    // 请求失败
    public static RespInfo requestError(String responseMessage) {
        return new RespInfo(ResponseConstants.REQUEST_FAIL.getRetCode(), responseMessage);
    }

    // 成功调用，并放回信息
    public static RespInfo requestSuccess(String responseMessage, Object resultSet) {
        return new RespInfo(ResponseConstants.REQUEST_SUCCESS.getRetCode()
                ,responseMessage, resultSet);
    }

    // 成功调用，并放回信息
    public static RespInfo requestSuccess(String responseCode, String responseMessage, Object resultSet) {
        return new RespInfo( responseCode, responseMessage, resultSet);
    }
}
