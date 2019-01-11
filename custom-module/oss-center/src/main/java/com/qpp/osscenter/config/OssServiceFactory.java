package com.qpp.osscenter.config;


import com.qpp.common.constant.enums.FileType;
import com.qpp.osscenter.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


/**
 * FileService工厂<br>
 * 将各个实现类放入map
*/
@Configuration
public class OssServiceFactory {

	private Map<FileType, FileService> map = new HashMap<>();

 
	@Autowired
	private FileService aliyunOssServiceImpl;
	
	@Autowired
	private FileService qiniuOssServiceImpl;
	

	@PostConstruct
	public void init() {
		map.put(FileType.ALIYUN, aliyunOssServiceImpl);
		map.put(FileType.QINIU ,  qiniuOssServiceImpl);
	}

	public FileService getFileService(String fileType) {

		return map.get(FileType.valueOf(fileType));
	}
}
