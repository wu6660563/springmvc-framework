package com.geeku.common.filter;

import java.util.Enumeration;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.constructs.blocking.LockTimeoutException;
import net.sf.ehcache.constructs.web.AlreadyCommittedException;
import net.sf.ehcache.constructs.web.AlreadyGzippedException;
import net.sf.ehcache.constructs.web.filter.FilterNonReentrantException;
import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName PageEhCacheFilter
 * @Description 页面缓存过滤器，采用EhCache方式
 * 	这里的PageEhCacheFilter继承了SimplePageCachingFilter
 * 	一般情况下SimplePageCachingFilter就够用了，这里是为了满足当前系统需求才做了覆盖操作。
 * 	使用SimplePageCachingFilter需要在web.xml中配置cacheName，cacheName默认是SimplePageCachingFilter
 * 	对应ehcache.xml中的cache配置。
 * @author Nick
 * @version 1.0
 * @Date 2016年7月26日 下午12:01:14
 */
public class PageEhCacheFilter extends SimplePageCachingFilter {

	private static Logger logger = LoggerFactory
			.getLogger(PageEhCacheFilter.class);

	private final static String FILTER_URL_PATTERNS = "patterns";
	private static String[] cacheURLs;

	/**
	 * @Title PageEhCacheFilter init
	 * @throws CacheException void
	 * @Description 初始化
	 * @author Nick
	 */
	private void init() throws CacheException {
		String patterns = filterConfig.getInitParameter(FILTER_URL_PATTERNS);
		cacheURLs = StringUtils.split(patterns, ",");
	}

	/**
	 * @Title doFilter
	 * @Description 拦截核心器，使用拦截的方式必须实现序列化
	 * @param request
	 * @param response
	 * @param chain
	 * @throws AlreadyGzippedException
	 * @throws AlreadyCommittedException
	 * @throws FilterNonReentrantException
	 * @throws LockTimeoutException
	 * @throws Exception
	 */
	@Override
	protected void doFilter(final HttpServletRequest request,
			final HttpServletResponse response, final FilterChain chain)
			throws AlreadyGzippedException, AlreadyCommittedException,
			FilterNonReentrantException, LockTimeoutException, Exception {
		if (cacheURLs == null) {
			init();
		}

		String url = request.getRequestURI();
		boolean flag = false;
		if (cacheURLs != null && cacheURLs.length > 0) {
			for (String cacheURL : cacheURLs) {
				if (url.contains(cacheURL.trim())) {
					flag = true;
					break;
				}
			}
		}
		// 如果包含我们要缓存的url 就缓存该页面，否则执行正常的页面转向
		if (flag) {
			String query = request.getQueryString();
			if (query != null) {
				query = "?" + query;
			}
			logger.info("当前请求被缓存：" + url + query);
			super.doFilter(request, response, chain);
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * @Title PageEhCacheFilter headerContains
	 * @param request
	 * @param header
	 * @param value
	 * @return boolean
	 * @Description 解析浏览器类型
	 * @author Nick
	 */
	private boolean headerContains(final HttpServletRequest request,
			final String header, final String value) {
		logRequestHeaders(request);
		final Enumeration<String> accepted = request.getHeaders(header);
		while (accepted.hasMoreElements()) {
			final String headerValue = accepted.nextElement();
			if (headerValue.indexOf(value) != -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @Title acceptsGzipEncoding
	 * @Description 兼容ie6/7 gzip压缩
	 * @param request
	 * @return 压缩之后的GZIP
	 */
	@Override
	protected boolean acceptsGzipEncoding(HttpServletRequest request) {
		boolean ie6 = headerContains(request, "User-Agent", "MSIE 6.0");
		boolean ie7 = headerContains(request, "User-Agent", "MSIE 7.0");
		return acceptsEncoding(request, "gzip") || ie6 || ie7;
	}

}
