package com.geeku.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.geeku.util.conve.DateConvert;

/**
 * @ClassName BeanUtils
 * @Description 
 *              BeanUtils工具类，主要用于Model转换Vo，使用Apache下面Common包的BeanUtils
 * @author Nick
 * @version 1.0
 * @Date 2016年7月23日 上午11:32:29
 */
public class GeekBeanUtil extends BeanUtils {
	
	static {
	       ConvertUtils.register(new DateConvert(), java.util.Date.class);
	       ConvertUtils.register(new DateConvert(), java.sql.Date.class);
	}


	private static Logger logger = LoggerFactory.getLogger(GeekBeanUtil.class);

	public static void copyPropertie(Object dest, Object source) {
		try {
			BeanUtils.copyProperties(dest, source);
		} catch (IllegalAccessException e) {
			logger.error("copyPropertie IllegalAccessException:" + e);
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			logger.error("copyPropertie InvocationTargetException:" + e);
			e.printStackTrace();
		}
	}

	/**
	 * @Title GeekBeanUtil MapToBean
	 * @param map
	 * @param obj
	 *            void
	 * @Description Map转换成Bean
	 * @author Nick
	 */
	public static void MapToBean(Map<String, Object> map, Object obj) {
		try {
			populate(obj, map);
		} catch (IllegalAccessException e) {
			logger.error("MapToBean IllegalAccessException:" + e);
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			logger.error("MapToBean InvocationTargetException:" + e);
			e.printStackTrace();
		}
	}

	public static Map<String, Object> BeanToMap(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo
					.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!"class".equals(key)) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);

					map.put(key, value);
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("transBean2Map Error " + e);
		}

		return map;
	}

}
