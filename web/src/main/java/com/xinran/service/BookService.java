
package com.xinran.service;

import java.util.List;

import com.xinran.pojo.Book;

/**
 * @author 高海军 帝奇 Apr 6, 2015 5:07:09 PM
 */
public interface BookService {

    List<Book> findAllWithPagenate(int limit, int offset);
    
    Book findBookByISBN(String isbn);
    
//    Book findBookByISBN(String isbn, boolean local);
    
    Book findBookById(Long id);

	List<Book> queryByTitle(String keyword);

    List<Book> queryByTitleWithPagenate(String keyword, int limit, int offset);

}
