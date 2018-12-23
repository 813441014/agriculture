package com.qpp.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

/**
 * @ClassName: GzippedInputStreamWrapper
 * @Description: TODO 压缩
 * @author qipengpai
 * @date 2017/10/8 3:56:14
 */
@Slf4j
public class GzippedOutputStreamWrapper extends HttpServletResponseWrapper {


    ByteArrayOutputStream output;
    FilterServletOutputStream filterOutput;
    HttpServletResponse httpServletResponse;

    public GzippedOutputStreamWrapper(HttpServletResponse response) {
        super(response);
        this.httpServletResponse= response;
        output = new ByteArrayOutputStream();
    }

    @Override
    public ServletOutputStream getOutputStream() throws IOException {
        if (filterOutput == null) {
            filterOutput = new FilterServletOutputStream(output);
        }
        return filterOutput;
    }

    public byte[] getDataStream() {
        return output.toByteArray();
    }

    public HttpServletResponse getHttpServletResponse() {
		return httpServletResponse;
	}

	/** 
     * @Title: sendMessage 
     * @Description: TODO(gzip压缩响应) 
     * @param @param response
     * @param @param msg
     * @param @throws IOException    设定文件 
     * @return void    返回类型 
     * @throws 
     */
    public static void sendMessage(HttpServletResponse response, String msg) throws IOException {
        try( GZIPOutputStream gOut= new GZIPOutputStream(response.getOutputStream()) ){
            gOut.write(msg.getBytes("UTF-8"));
        }catch (Exception e){
            log.error("gzip压缩失败",e);
        }
    }
}
