package com.jt.manage.service;

import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;

public interface FileUploadService {

	//定义文件上传操作
	PicUploadResult uploadFile(MultipartFile uploadFile);
}
