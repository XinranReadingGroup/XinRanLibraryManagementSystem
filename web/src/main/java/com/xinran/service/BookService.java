
package com.xinran.service;

import java.util.List;

import com.xinran.pojo.Book;

/**
 * @author 高海军 帝奇 Apr 6, 2015 5:07:09 PM
 */
public interface BookService {

    public List<Book> findAllWithPagenate(int limit, int offset);
}