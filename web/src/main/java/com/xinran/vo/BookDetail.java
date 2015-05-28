
package com.xinran.vo;

import lombok.Data;

import com.xinran.pojo.Book;
import com.xinran.pojo.OnOffStockRecord;

/**
 * @author 高海军 帝奇 Apr 8, 2015 7:42:23 AM
 */
@Data
public class BookDetail {

    private Book Book;
    private OnOffStockRecord onOffStockRecord;
    
    // 捐书人或者享书人VO
    private BasicUserVO      userVO;

    private BookLocationVO   bookLocationVO;

}
