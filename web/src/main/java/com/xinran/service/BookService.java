
package com.xinran.service;

import java.util.List;

import com.xinran.pojo.Book;
import com.xinran.pojo.OnOffStockRecord;

/**
 * @author 高海军 帝奇 Apr 6, 2015 5:07:09 PM
 */
public interface BookService {

    public List<Book> findAllWithPagenate(int limit, int offset);
    
    
    Book findBookByISBN(String isbn);
    
    /**
     * 书本上架。目前由捐书和享书提供书本来源。
     * 
     * @return 如果上架成功，则返回完整记录，否则返回NULL
     */
    OnOffStockRecord onStock(OnOffStockRecord record);
}
