package com.xinran.pojo;

import lombok.Data;


/**
 * 
 * 
 * @author zhuangyao.zy
 *
 */
@Data
public class Pagination {

	/**
	 * Get or set page size, default is 20.
	 */
	private  Integer size = 20;
	
	
	/**
	 * Get or set current page index , default is 1.
	 */
	private  Integer current = 1;
	
	
	/**
	 * Get or set total, default is 0.
	 */
	private  Integer total;
	
	
	/**
	 * Get start index for this page, calculated from current index and page size. 
	 * Value 0 for first, MYSQL first.
	 */
	public Integer getStart(){
		return (current - 1) * size;
	}
	
	/**
	 * Get end index for this page, calculated from current index and page size.
	 */
	public Integer getEnd(){
		return size;
	}
	
	
	
	
}
