package com.geeku.util;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @ClassName EhCacheUtil
 * @Description 单例模式，EhCache工具类
 * @author Nick
 * @version 1.0
 * @Date 2016年7月27日 上午9:13:25
 */
public class EhCacheUtil {

	/**
	 * @Fields instance 实例
	 */
	private static EhCacheUtil instance = null;

	/**
	 * @Fields cacheManager EhCache缓存
	 */
	private static CacheManager cacheManager = null;

	/**
	 * @Fields CACHE_NAME 默认的缓存ID
	 */
	private static final String CACHE_NAME = "dataCache";

	/**
	 * @Title EhCacheUtil
	 * 
	 * @Description 构造方法私有化
	 */
	private EhCacheUtil() {
		cacheManager = CacheManager.create();
	}

	/**
	 * @Title EhCacheUtil getInstance
	 * @return EhCacheUtil
	 * @Description 单例模式
	 */
	public static EhCacheUtil getInstance() {
		if (instance == null) {
			instance = new EhCacheUtil();
		}
		return instance;
	}

	/**
	 * @Title EhCacheUtil put
	 * @param cacheName 缓存ID
	 * @param key 缓存Key
	 * @param value 缓存值
	 * @Description 把缓存加入到Ehcache中
	 */
	public void put(String cacheName, Serializable key, Serializable value) {
		Cache dataCache = cacheManager.getCache(cacheName);
		Element element = new Element(key, value);
		dataCache.put(element);
	}

	public void put(String cacheName, Object key, Object value) {
		Cache dataCache = cacheManager.getCache(cacheName);
		Element element = new Element(key, value);
		dataCache.put(element);
	}

	public void put(Serializable key, Serializable value) {
		put(CACHE_NAME, key, value);
	}

	public void put(Object key, Object value) {
		put(CACHE_NAME, key, value);
	}

	public Object get(String cacheName, Object key) {
		Cache dataCache = cacheManager.getCache(cacheName);
		Element element = dataCache.get(key);
		return element.getObjectValue();
	}

	public Object get(String cacheName, Serializable key) {
		Cache dataCache = cacheManager.getCache(cacheName);
		Element element = dataCache.get(key);
		return element.getObjectValue();
	}

	public Object get(Serializable key) {
		return get(CACHE_NAME, key);
	}

}
