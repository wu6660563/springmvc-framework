package com.geeku.util;

import java.io.Serializable;
import java.util.List;

/**
 * 分页工具类
 * 
 * @Author ji.jiang
 */
@SuppressWarnings("serial")
public class PageModel implements Serializable {
	/** 定义默认每页显示的记录数 */
	private static final int PAGE_SIZE = 10;

	/** 当前页码 */
	private int pageIndex = 1;
	/** 每页显示的数量 */
	private int pageSize = 10;
	/** 总记录条数 */
	private int recordCount;
	/** 总页数 */
	private int totalPage;
	/** 当前页查询后List形式的结果集 */
	private List results;

	/** setter and getter method */
	public int getPageIndex() {
		/** 当前页码不能小于1 */
		this.pageIndex = pageIndex <= 1 ? 1 : pageIndex;
		/** 当前页码不能大于总页数 */
		int totalPage = (this.recordCount - 1) / this.getPageSize() + 1;
		return pageIndex > totalPage ? totalPage : pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	
	
	public void setPageIndex(String pageIndex) {
		this.pageIndex = Integer.valueOf(pageIndex);
	}

	public int getPageSize() {
		return pageSize <= 0 ? PAGE_SIZE : pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
	public void setPageSize(String pageSize) {
		this.pageSize = Integer.valueOf(pageSize);
	}

	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getTotalPage() {
		int i = (this.recordCount % this.pageSize == 0) ? this.recordCount
				/ this.pageSize : this.recordCount / this.pageSize + 1;
		return i;

	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/** limit第一个问号的值 */
	public int getStartRow() {
		return (this.getPageIndex() - 1) * this.getPageSize();
	}

	public List getResults() {
		return results;
	}

	public void setResults(List results) {
		this.results = results;
	}
	
}
