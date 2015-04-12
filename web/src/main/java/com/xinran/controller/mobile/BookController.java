
package com.xinran.controller.mobile;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xinran.constant.ApplicationConstant;
import com.xinran.constant.BookType;
import com.xinran.constant.BorrowStatus;
import com.xinran.pojo.Book;
import com.xinran.pojo.OnOffStockRecord;
import com.xinran.service.BookService;
import com.xinran.vo.AjaxResult;
import com.xinran.vo.builder.AjaxResultBuilder;

/**
 * 
 * @author zhuangyao.zy
 *
 */
@RestController
@RequestMapping("/mobile")
public class BookController {


    @Autowired
    private BookService bookService;

    @RequestMapping("/book/isbn/{isbn}")
    public @ResponseBody AjaxResult getBookByISBN(@PathVariable(value = "isbn") String isbn, HttpServletRequest request) {
    	Book book = bookService.findBookByISBN(isbn);
    	return AjaxResultBuilder.buildSuccessfulResult(book);
    }
    
    @RequestMapping("/book/donate/{bookId}")
    public @ResponseBody AjaxResult donate(@PathVariable(value="bookId") Long bookId, 
                                           @RequestParam("location") Long location,
                                           @RequestParam("phone") String phone,
    		HttpServletRequest request){
    	return AjaxResultBuilder.buildSuccessfulResult(onStock(bookId,location,phone,request,BookType.DONATED));
    }
    
    @RequestMapping("/book/share/{bookId}")
    public @ResponseBody AjaxResult share(@PathVariable(value="bookId") Long bookId, 
                                          @RequestParam("location") Long location, @RequestParam("phone") String phone,
    		HttpServletRequest request){
    	return AjaxResultBuilder.buildSuccessfulResult(onStock(bookId,location,phone,request,BookType.SHARED));
    }
    
    private OnOffStockRecord onStock(Long bookId, Long location, String phone,
    		HttpServletRequest request, BookType bookType){
    	OnOffStockRecord record = new OnOffStockRecord();
        record.setOwnerUserId((Long) request.getSession().getAttribute(ApplicationConstant.USER_ID));
    	record.setOwnerPhone(phone);
        record.setBookType(bookType.getType());
    	record.setLocation(location);
    	record.setBookId(bookId);
    	record.setBorrowStatus(BorrowStatus.UNBORROWED.getStatus());
    	record = bookService.onStock(record);
    	return record;
    	
    }

}
