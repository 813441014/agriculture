package com.qpp.osscenter.service;


import com.qpp.common.base.PageInfo;
import com.qpp.osscenter.entity.FileInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 文件service 目前仅支持阿里云oss,七牛云
*/
public interface FileService {

	FileInfo upload(MultipartFile file) throws Exception;

	void delete(FileInfo fileInfo);
	
	FileInfo getById(String id);
	
	PageInfo<FileInfo> findList(Map<String, Object> params);

}
