package com.xinran.service;

import java.util.Map;

/**
 * 
 * 豆瓣服务相关
 * 
 * 
 * @author zhuangyao.zy
 *
 */
public interface DouBanService {

	/**
	 * 
	 * 根据ISBN从豆瓣获取书籍信息。
	 * 
	 * @return 返回MAP格式的图书信息，key及value类型参考JSON
	 */
	Map<String, Object> getBookByISBN(String isbn);
	
}
