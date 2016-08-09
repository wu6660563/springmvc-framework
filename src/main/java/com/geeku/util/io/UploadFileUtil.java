package com.geeku.util.io;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.geeku.util.DateUtil;

/**
 * 文件上传，删除等操作工具类（存放目录在应用之外）
 * 
 * 需要在发布的TOMCAT中添加虚拟路径：
 * 在server.xml的Host节点中添加<Context docBase="D:/work_bak/projects/imgload" path="/imgload" debug="0" reloadable="true"/>
 *
 */
public class UploadFileUtil {
	private static Logger logger = LoggerFactory.getLogger(UploadFileUtil.class);
	
	private static String partRealPath = PropertiesUtil.readKey("partRealPath");//基本路径
	private static String pathVisitPath = PropertiesUtil.readKey("pathVisitPath");//虚拟访问路径
	
	/**
	 * 根据上传文件的名称生成存放文件的路径
	 * @param myfile 上传文件的名称
	 * @param specificPath 指定的具体路径
	 * @return 文件存放路径
	 * @throws Exception
	 */
	public static String createFilePath(String fileName,String specificPath){
		Date currTime = new Date();
		String fileDiv = DateUtil.formatYear(currTime)+"-"+DateUtil.formatMonth(currTime);//以当前年份-月份做为存放文件的文件夹
		
		String imgpath = "";
		if (fileName != null && !"".equals(fileName.trim())) {
			String fileNameStart = UUID.randomUUID().toString().replace("-", "");//生成UUID做为文件唯一名称
			String fileNameEnd = fileName.substring(fileName.lastIndexOf("."));// 获取文件后缀名
			String newFileName = fileNameStart + fileNameEnd;//文件名称
			imgpath = pathVisitPath + specificPath + fileDiv+ "/" + newFileName;//文件存放路径
		} else {
			logger.info("根据上传文件的名称生成存放文件的路径：###---给定的原文件名称为空！--- ");
		}
		return imgpath;
	}
	
	/**
	 * 上传文件到指定目录
	 * @param currFile 文件输入流
	 * @param filePath 文件存放路径
	 * @return 
	 * @throws Exception
	 */
	public static boolean addFile(MultipartFile currFile,String filePath)
			throws Exception {
		boolean opt = false;//上传是否成功
		String fileFolder =  filePath.substring(0,filePath.lastIndexOf("/"));
		if (!currFile.isEmpty() && currFile.getSize() > 0) {
			File file_div = new File(partRealPath + fileFolder);
			if (!file_div.exists()) {
				file_div.mkdirs();
			}
			String realPath = partRealPath + filePath;
			logger.info("###---文件上传路径到服务器的realPath: " + realPath);
			try {
				File uploadFile = new File(realPath);
				currFile.transferTo(uploadFile);
				opt = true;
				logger.info("###---文件上传成功---");
			} catch (Exception e) {
				logger.info("###---文件上传失败---");
				throw new Exception(e.getMessage());
			}
		} else {
			logger.info("###---没有上传任何文件或文件为空--- ");
		}
		return opt;
	}
	
	
	/**
	 * 删除文件
	 * @param filePath 文件存放路径
	 * @return 是否成功
	 * @throws Exception 
	 */
	public static boolean deleteFile(String filePath) {
		boolean opt = false;//删除是否成功
		if (filePath != null && !"".equals(filePath.trim())) {
			String fileFealPath = partRealPath + "/" + filePath;
			try {
				File file = new File(fileFealPath);
				if (file.isFile() && file.exists()) {
					file.delete();
					opt = true;
					logger.info("###删除文件成功，文件路径为：" + fileFealPath);
				}
			} catch (Exception e) {
				logger.info("###删除文件失败，文件路径为：" + fileFealPath);
			}
		}else{
			logger.info("###没有给出要删除文件的路径！");
		}
		
		return opt;
	}
	
	/**
	 * 判断图片后缀名是否允许上传
	 * 
	 * @param fileName	文件名
	 * @return
	 */
	public static boolean judgeFileType(String fileName){
		String fileNameEnd = fileName.substring(fileName.lastIndexOf("."));// 获取图片后缀名
		
		return fileNameEnd.equals(".jpeg") || fileNameEnd.equals(".jpg") || fileNameEnd.equals(".gif") 
				|| fileNameEnd.equals(".png");
	}
	
}
