package com.jt.manage.service;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	/**
	 * 编程思路
	 * 1.获取图片的名称
	 * 2.截取图片的类型
	 * 3.判断是否为图片格式jpg|gif|png
	 * 4.判断是否为恶意程序
	 * 5.
	 * 6.
	 * 7.
	 * 8.
	 */
	@Override
	public PicUploadResult uploadFile(MultipartFile uploadFile) {
		PicUploadResult result = new PicUploadResult();
		//1.获取图片的名称	aaa.jpg
		String fileName = uploadFile.getOriginalFilename();
		//2.获取图片的类型	.jpg
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		//3.判断是否为图片格式
		if(!fileName.matches("^.*(jpg|png|gif)&")){
			//表示不是图片类型
			result.setError(1);
			return result;	//如果程序出错，则直接返回对象
		}
		//4.判断是否为恶意程序
		/**
		 * 判断一个文件是否为图片，一般获取图片的高度和宽度
		 * 如果二者都不为0则表示文件为图片
		 */
		//4.1通过工具类判断是否为图片
		try {
			BufferedImage bufferedImage = ImageIO.read(uploadFile.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
