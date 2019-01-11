package com.qpp.osscenter.controller;


import com.qpp.common.annotation.log.Log;
import com.qpp.common.base.PageInfo;
import com.qpp.common.base.RespInfo;
import com.qpp.common.constant.enums.FileType;
import com.qpp.osscenter.config.OssServiceFactory;
import com.qpp.osscenter.entity.FileInfo;
import com.qpp.osscenter.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 作者 owen E-mail: 624191343@qq.com
 * @version 创建时间：2017年11月12日 上午22:57:51
*  文件上传 同步oss db双写 目前仅实现了阿里云,七牛云
*  参考src/main/view/upload.html
*/
@RestController
public class FileController {

	@Autowired
	private OssServiceFactory fileServiceFactory;

	/**
	 * 文件上传
	 * 根据fileType选择上传方式
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@Log(desc = "file-center:FileController:upload")
	@PostMapping("/files-anon")
	public FileInfo upload(@RequestParam("file") MultipartFile file) throws Exception {
		String fileType = FileType.QINIU.toString();
		FileService fileService = fileServiceFactory.getFileService(fileType);
		return fileService.upload(file);
	}

	/**
	 * layui富文本文件自定义上传
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@Log(desc = "file-center:FileController:uploadLayui")
	@PostMapping("/files/layui")
	public Map<String, Object> uploadLayui(@RequestParam("file") MultipartFile file )
			throws Exception {
		
		FileInfo fileInfo = upload(file);

		Map<String, Object> map = new HashMap<>();
		map.put("code", 0);
		Map<String, Object> data = new HashMap<>();
		data.put("src", fileInfo.getUrl());
		map.put("data", data);

		return map;
	}

	/**
	 * 文件删除
	 * @param id
	 */
	@Log(desc = "file-center:FileController:delete")
	@DeleteMapping("/files/{id}")
	public RespInfo delete(@PathVariable String id) {

		try{
			FileInfo fileInfo = fileServiceFactory.getFileService(FileType.QINIU.toString()).getById(id);
			if (fileInfo != null) {
				FileService fileService = fileServiceFactory.getFileService(fileInfo.getSource());
				fileService.delete(fileInfo);
			}
			return RespInfo.requestSuccess("文件删除成功","");
		}catch (Exception ex){
			return RespInfo.requestError("文件删除失败");
		}

	}
 
	/**
	 * 文件查询
	 * @param params
	 * @return
	 */
	@GetMapping("/files")
	public PageInfo<FileInfo> findFiles(@RequestParam Map<String, Object> params) {
		return  fileServiceFactory.getFileService(FileType.QINIU.toString()).findList(params);
	}
}
