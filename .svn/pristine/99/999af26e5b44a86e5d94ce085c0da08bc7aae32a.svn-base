package com.geeku.util.conve;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 工具类:将其它文件格式转换为PDF格式
 * @author JiangJi
 */
public class DOC2PDFUtil {
	private static Logger logger = LoggerFactory.getLogger(DOC2PDFUtil.class);

	private static OfficeManager officeManager = null;

	/*
	 * 文件格式转换为PDF
	 * @param docFile 源文件
	 * @param outputFile 输出文件
	 */
	public static synchronized void doc2pdf(File docFile, File outputFile) throws Exception {
		//对以下格式的文件进行统一编码操作
		if (docFile.getPath().endsWith(".txt")
				|| docFile.getPath().endsWith(".jsp")
				|| docFile.getPath().endsWith(".aspx")
				|| docFile.getPath().endsWith(".css")
				|| docFile.getPath().endsWith(".js")
				|| docFile.getPath().endsWith(".xml")) {
			String fileEncode=EncodingDetect.getJavaEncode(docFile.getPath()); //获得指定文件的编码  
			InputStreamReader in = new InputStreamReader(new FileInputStream(docFile), fileEncode);
			OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(docFile.getPath()+".dstmp"),"gb2312");
			char[] cbuf = new char[1024];
			int n;
			while ((n = in.read(cbuf)) != -1) {
				out.write(cbuf, 0, n);
			}
			in.close();
			out.close();
			docFile = new File(docFile.getPath()+".dstmp");
		}
		
		
		if (docFile.exists()) {
			try {
				OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
				converter.convert(docFile, outputFile);
			} catch (Exception e) {
				logger.info("****文件转换为pdf时发生异常****");
				throw e;
			} finally {
				if (docFile.getPath().endsWith(".dstmp")){
					docFile.delete();
				}
			}
		} else {
			logger.info("文档不存在，无法转换****");
		}
	}

	//启动OpenOffice服务
	public static void startService(String officeHome) {
		DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
		try {
			logger.info("准备启动服务....");
			configuration.setOfficeHome(officeHome);// 设置OpenOffice.org安装目录
			configuration.setPortNumbers(8100); // 设置转换端口，默认为8100
			configuration.setTaskExecutionTimeout(1000 * 60 * 5L);// 设置任务执行超时为5分钟
			configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24L);// 设置任务队列超时为24小时

			officeManager = configuration.buildOfficeManager();
			officeManager.start(); // 启动服务
			logger.info("office转换服务启动成功!");
		} catch (Exception ce) {
			logger.info("office转换服务启动失败!详细信息:");
			ce.printStackTrace();
		}
	}

	//停止OpenOffice服务
	public static void stopService() {
		try {
			logger.info("关闭office转换器....");
			if (officeManager != null) {
				officeManager.stop();
			}
			logger.info("关闭office转换器成功!");
		} catch (Exception e) {
			logger.info("关闭office转换器失败!");
			e.printStackTrace();
		}
	}
	
}
