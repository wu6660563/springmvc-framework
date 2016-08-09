package com.geeku.util.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件下载的工具类
 * 
 * @Author ji.jiang
 */
public final class DownloadFileTools {

	/**
	 * 通过浏览器直接下载文件
	 * 
	 * @param request
	 * @param response
	 * @param storeName 文件存放路径（服务器上）
	 * @param contentType 类型
	 * @param realName 文件真实名称
	 * @throws Exception
	 */
	public static void download(HttpServletRequest request,
			HttpServletResponse response, String storeName, String contentType,
			String realName) throws Exception {
		//response.setCharacterEncoding("UTF-8");
		response.setContentType(contentType);
		
		//对文件流输出下载的中文文件名进行编码 屏蔽各种浏览器版本的差异性
		String userAgent = request.getHeader("User-Agent"); 
		if (userAgent.contains("MSIE")||userAgent.contains("Trident")) {//针对IE或者以IE为内核的浏览器
			realName = java.net.URLEncoder.encode(realName, "UTF-8");
			//realName = realName.replace("+", "%20");//替换空格 
		} else {
			realName = new String(realName.getBytes("UTF-8"), "ISO-8859-1");//非IE浏览器的处理
		}
		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		long fileLength = new File(storeName).length();
		response.setHeader("Content-disposition", "attachment; filename=\""+ realName +"\"");
		response.setHeader("Content-Length", String.valueOf(fileLength));
		bis = new BufferedInputStream(new FileInputStream(storeName));
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		bis.close();
		bos.close();
	}
}
