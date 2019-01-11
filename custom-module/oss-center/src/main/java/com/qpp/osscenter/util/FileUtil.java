package com.qpp.osscenter.util;

import com.qpp.common.utils.file.FileUtils;
import com.qpp.osscenter.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * @ClassName FileUtil
 * @Description TODO
 * @Author qipengpai
 * @Date 2018/12/25 22:03
 * @Version 1.0.1
 */
public class FileUtil {

    public static FileInfo getFileInfo(MultipartFile file) throws Exception {
        String md5 = FileUtils.fileMd5(file.getInputStream());

        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(md5);// 将文件的md5设置为文件表的id
        fileInfo.setName(file.getOriginalFilename());
        fileInfo.setContentType(file.getContentType());
        fileInfo.setIsImg(fileInfo.getContentType().startsWith("image/"));
        fileInfo.setSize(file.getSize());
        fileInfo.setCreateTime(new Date());

        return fileInfo;
    }
}
