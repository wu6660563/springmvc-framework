package com.geeku.util.conve;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.Converter;

/**
 * @ClassName DateConvert
 * @Description 日期Converter
 * @author Nick
 * @version 1.0
 * @Date 2016年7月26日 下午3:27:28
 */
public class DateConvert implements Converter {

	public Object convert(Class type, Object obj) {
		String p = (String) obj;
		if (p == null || p.trim().length() == 0) {
			return null;
		}
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.parse(p.trim());
		} catch (Exception e) {
			try {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				return df.parse(p.trim());
			} catch (ParseException ex) {
				return null;
			}
		}
	}

}
