package com.geeku.util;

import com.geeku.common.RedisManager;


/**
 * @ClassName RedisUtil
 * @Description Redis工具类，使用RedisManager链接，基于Jedis
 * 				不采用spring-data-redis，因为目前项目较为简单，不过渡设计
 * @author Nick
 * @version 1.0
 * @Date 2016年8月2日 下午7:25:52
 */
public class RedisUtil {
	
	/**
	 * @param key Key
	 * @Description 根据Key获取Value
	 */
	public static String get(String key) {
		return RedisManager.getJedis().get(key);
	}
	
	/**
	 * @param key Key值
	 * @param value Value值
	 * @Description Key-value存入Redis里面，建议对象也转成Json存进Redis
	 */
	public static void set(String key, String value) {
		RedisManager.getJedis().set(key, value);
	}
	
	/**
	 * @param key
	 * @param expireSeconds 超时时间，单位秒
	 * @Description 设置某个Key超时时间
	 */
	public static void expire(String key, int expireSeconds) {
		RedisManager.getJedis().expire(key, expireSeconds);
	}
	
	/**
	 * @param key Key值
	 * @return Long
	 * @Description 删除Key
	 */
	public static Long del(String key) {
		return RedisManager.getJedis().del(key);
	}
	
	

}
