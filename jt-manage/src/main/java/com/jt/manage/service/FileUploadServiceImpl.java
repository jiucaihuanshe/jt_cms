package com.jt.manage.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.common.vo.PicUploadResult;

@Service
public class FileUploadServiceImpl implements FileUploadService {

	private String dirPath = "C:/tedu/workspace/jt-upload/";	//定义本地的磁盘路径
	private String url = "http://image.jt.com/";	//定义url访问路径
	/**
	 * 编程思路
	 * 1.获取图片的名称
	 * 2.截取图片的类型
	 * 3.判断是否为图片格式jpg|gif|png
	 * 4.判断是否为恶意程序
	 * 5.定义磁盘路径和url访问路径
	 * 6.准备以时间为界限的文件夹
	 * 7.让图片不重名 当前的时间毫秒数+三位随机数
	 * 8.创建文件夹
	 * 9.开始写盘操作
	 * 10.将数据准备好返回给客户端
	 */
	@Override
	public PicUploadResult uploadFile(MultipartFile uploadFile) {
		PicUploadResult result = new PicUploadResult();
		//1.获取图片的名称	aaa.jpg
		String fileName = uploadFile.getOriginalFilename();
		//2.获取图片的类型	.jpg
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		//3.判断是否为图片格式
		if(!fileName.matches("^.*(jpg|png|gif)$")){
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
			//4.2获取图片的高度和宽度
			int height = bufferedImage.getHeight();
			int width = bufferedImage.getWidth();
			if(height==0 || width==0){
				result.setError(1);//表示非法程序
				return result;
			}
			
			//6.准备时间文件夹并且时间格式yyyy/MM/dd/HH
			String datePathDir = new SimpleDateFormat("yyyy/MM/dd/HH").format(new Date());
			//7.准备时间	毫秒值+3位随机数
			String millis = System.currentTimeMillis()+"";
			Random random = new Random();
			int randomNum = random.nextInt(999);
			String randomPath = millis+randomNum;
			//8.创建文件夹
			//C:/tedu/workspace/jt-upload/yyyy/MM/dd/HH
			String LocalPath = dirPath+datePathDir;
			File file = new File(LocalPath);
			if(!file.exists()){
				file.mkdirs();	//如果文件夹不存在，则创建文件夹
			}
			//9.通过工具类实现写盘操作
			String LocalPathFile = LocalPath+"/"+randomPath+fileName;
			//文件写盘 xxxx/aaa.jpg
			uploadFile.transferTo(new File(LocalPathFile));
			//10.将数据准备好返回给客户端
			result.setHeight(height+"");
			result.setWidth(width+"");
			//代表虚拟的全路径
			String urlPath =url+datePathDir+"/"+randomPath+fileName;
			result.setUrl(urlPath);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
