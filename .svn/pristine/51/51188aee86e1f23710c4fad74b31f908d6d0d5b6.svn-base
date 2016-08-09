package com.geeku.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 读取配置文件中系统参数配置信息的工具类
 * 
 * @Author ji.jiang
 */
public final class PropertiesUtil {
	private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	
	private static final String DEFULT_CONFIG_FILE = "app.properties";//默认的配置文件

	private static class MCDHInstance {
		private static final PropertiesUtil Pro = new PropertiesUtil();
	}

	public static Properties readProerties(String path) {
		Properties proper = MCDHInstance.Pro.readProperties(path);
		return proper;
	}
	
	/**
	 * 从默认的配置文件中读取参数值
	 * @param key 参数key
	 * @return
	 */
	public static String readKey(String key) {
		Properties proper = MCDHInstance.Pro.readProperties(DEFULT_CONFIG_FILE);
		return (String) proper.get(key);
	}

	/**
	 * 从给定的配置文件中读取参数值
	 * @param path 指定的配置文件全路径
	 * @param key 参数key
	 * @return
	 */
	public static String readKey(String path, String key) {
		Properties proper = MCDHInstance.Pro.readProperties(path);
		return (String) proper.get(key);
	}

	private Properties readProperties(String path) {
		if ("".equals(path) || path == null) {
			path = DEFULT_CONFIG_FILE;
		}
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(path);
		Properties proper = new Properties();
		try {
			proper.load(in);
		} catch (IOException e) {
			logger.error("加载系统参数配置文件 app.properties 出错!");
		}
		return proper;
	}
}
