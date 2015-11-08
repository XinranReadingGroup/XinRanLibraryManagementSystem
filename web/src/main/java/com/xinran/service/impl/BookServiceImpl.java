
package com.xinran.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xinran.dao.mapper.BookMapper;
import com.xinran.pojo.Book;
import com.xinran.service.BookService;
import com.xinran.service.DouBanService;

/**
 * @author 高海军 帝奇 Apr 6, 2015 7:21:46 PM
 */
@Service
public class BookServiceImpl implements BookService {
	
	private static final Logger LOG = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookMapper bookMapper;
    

    
    @Autowired
    private DouBanService douBanService;
    
    
    @Override
    public Book findBookById(Long id){
    	if(id == null || id < 1L){
    		return null;
    	}
    	return bookMapper.findById(id);
    }

    @Override
    public List<Book> findAllWithPagenate(int limit, int offset) {
        return bookMapper.findAllWithPagenate(limit, offset);
    }

    

	@Override
	public Book findBookByISBN(String isbn, boolean local) {
		if(StringUtils.isBlank(isbn)){
			return null;
		}
		Book book = bookMapper.findByISBN(isbn);
		if(book == null && !local){
			JSONObject douBanData = douBanService.getBookByISBN(isbn);
			if(douBanData != null){
				book = convertDouBanData2Book(douBanData);
				try{
					bookMapper.add(book);
				}catch(Exception e){
					LOG.error("Error to add book to db", e);
				}
			}
		}
		return book;
	}
	
	@Override
	public Book findBookByISBN(String isbn) {
		if(StringUtils.isBlank(isbn)){
			return null;
		}
		Book book = bookMapper.findByISBN(isbn);
		if(book == null){
			JSONObject douBanData = douBanService.getBookByISBN(isbn);
			if(douBanData != null){
				book = convertDouBanData2Book(douBanData);
				try{
					//TODO 未来可以考虑在db层使用unique 来优化. 解决https://github.com/XinranReadingGroup/XinRanLibraryManagementSystem/issues/5 
					synchronized(this){
						book = bookMapper.findByISBN(isbn);
						if(null ==book){
							bookMapper.add(book);
						}

					}
				}catch(Exception e){
					LOG.error("Error to add book to db", e);
				}
			}
		}
		return book;
	}
	
	@Override
	public List<Book> queryByTitle(String keyword){
		List<Book> books = null;
		if(StringUtils.isBlank(keyword)){
			return books;
		}
		
		return bookMapper.queryByTitle(keyword);
	}

	
	private Book convertDouBanData2Book(JSONObject douBanData) {
		
		Book book = new Book();
		
		// ISBN
		String isbn = douBanData.getString("isbn13");
		if(StringUtils.isBlank(isbn)){
			//说明该书的isbn不正确,或者在douban中未收录
			return null;
		}else{
			book.setIsbn(isbn);
		}
		
		
		// 书名
		book.setTitle(douBanData.getString("title"));
		
		// 作者，如果多个作者，以逗号拼接
		JSONArray authors = douBanData.getJSONArray("author");
		if(authors != null && authors.size() == 1){
			book.setAuthor(authors.getString(0));
		}else if(authors != null && authors.size() > 1){
			StringBuilder appender = new StringBuilder();
			for(int i=0; i < authors.size(); i++){
				appender.append(authors.getString(i));
				if(i < authors.size() - 1){
					appender.append(",");
				}
			}
			book.setAuthor(appender.toString());
		}else{
			book.setAuthor(StringUtils.EMPTY);
		}
		// 价格
		book.setPrice(douBanData.getString("price"));
		// 出版社
		book.setPublisher(douBanData.getString("publisher"));
		
		// 概述
		book.setSummary(douBanData.getString("summary"));
		// 图片
        book.setImgUrl(douBanData.getString("image"));
		return book;
	}

}
