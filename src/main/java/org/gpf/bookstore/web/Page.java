package org.gpf.bookstore.web;

import java.util.List;

public class Page<T> {

	private int pageNo; // 当前页数
	
	private List<T> list; // 当前页的list
	private int pageSize = 3; // 每页显示多少条数据
	private long totalItemNumber; // 共多少条记录
	
	public long getTotalItemNumber() {
		return totalItemNumber;
	}

	public void setTotalItemNumber(long totalItemNumber) {
		this.totalItemNumber = totalItemNumber;
	}

	/**
	 * 构造器中对pageNo初始化
	 * @param pageNo
	 */
	public Page(int pageNo) {
		super();
		this.pageNo = pageNo;
	}

	public int getPageNo() {
		
		if (pageNo < 0) {
			pageNo = 1;
		} else if (pageNo > getTotalPageNumber()) {
			pageNo = getTotalPageNumber();
		}
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 获取总页数
	 * @return
	 */
	public int getTotalPageNumber(){
		
		int totalPageNumber = (int) (totalItemNumber / pageSize);
		if (totalItemNumber % pageSize != 0) {
			totalPageNumber++;
		}
		return totalPageNumber;
	}
	
	/**
	 * 下一页？
	 * @return
	 */
	public boolean hasNext() {
		
		return getTotalPageNumber() > getPageNo();
	}
	
	/**
	 * 上一页？
	 * @return
	 */
	public boolean hasPrev(){
		
		return getPageNo() > 1;
	}
	
	/**
	 * 上一页
	 * @return
	 */
	public int getPrevPage(){
		
		return hasPrev() ? getPageNo() - 1 : 1;
	}
	
	/**
	 * 下一页
	 * @return
	 */
	public int  getNextPage() {
		
		return hasNext() ? getPageNo() + 1 : getTotalPageNumber();
	}
}
