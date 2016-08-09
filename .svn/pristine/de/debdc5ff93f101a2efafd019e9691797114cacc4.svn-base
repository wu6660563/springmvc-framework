package com.geeku.util;

import java.security.MessageDigest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.codec.Base64;

/**
 * 字符串工具类
 * 
 * @Author ji.jiang
 */
public class GeekStringUtil {
	/**
	 * 判断字符串不为空
	 * 
	 * @param str 需判断的字符串
	 * @return 是|非
	 */
	public static boolean notEmpty(String str) {
		return StringUtils.isNotEmpty(str);
	}
	
	/**
	 * 通过base64方式对字符串进行加密
	 * 
	 * @param passWord
	 *            明文
	 * @return 密文
	 */
	public static String setBase64Format(String passWord) {
		if (notEmpty(passWord)){
			passWord = Base64.encodeToString(passWord.getBytes());
		}
		return passWord;
	}

	/**
	 * 通过base64方式对字符串进行解密
	 * 
	 * @param passWord
	 *            密文
	 * @return 明文
	 */
	public static String getBase64Format(String passWord) {
		if (notEmpty(passWord)){
			byte[] strArr = Base64.decode(passWord);
			passWord = new String(strArr);
		}
		return passWord;
	}
	
	/**
	 * 通过MD5方式对字符串进行加密
	 * 
	 * @param param
	 *            明文
	 * @return 加密后的字符串
	 * @throws Exception
	 */
	public static String setMD5Format(String passWord) throws Exception {
		MessageDigest md = MessageDigest.getInstance("MD5");//创建MD5加密对象
		md.update(passWord.getBytes());//进行加密
		byte[] md5Bytes = md.digest();//获取加密后的字节数组长度

		String result = "";
		/**
		 * 将16位长度的字节数组转化成32位长度的字符串 (将其中的一位转化成16进制的二位，不够两位前面补零)
		 */
		for (int i = 0; i < md5Bytes.length; i++) {
			int temp = md5Bytes[i] & 0xFF;
			if (temp <= 0xf) {
				result += "0";
			}
			result += Integer.toHexString(temp);
		}
		return result;
	}

}
