
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
import com.xinran.constant.ExceptionCode;
import com.xinran.exception.BorrowOrReturnValidationException;
import com.xinran.pojo.Book;
import com.xinran.pojo.BorrowReturnRecord;
import com.xinran.pojo.OnOffStockRecord;
import com.xinran.service.BookService;
import com.xinran.service.OnOffStockRecordService;
import com.xinran.util.DateUtil;
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

    @Autowired
    private OnOffStockRecordService onOffStockRecordService;

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
    	OnOffStockRecord onStock = onStock(bookId,location,phone,request,BookType.DONATED);
        return AjaxResultBuilder.buildSuccessfulResult(onStock);
    }
    
    @RequestMapping("/book/share/{bookId}")
    public @ResponseBody AjaxResult share(@PathVariable(value="bookId") Long bookId, 
                                          @RequestParam("location") Long location, @RequestParam("phone") String phone,
    		HttpServletRequest request){
    	OnOffStockRecord onStock = onStock(bookId,location,phone,request,BookType.SHARED);
        return AjaxResultBuilder.buildSuccessfulResult(onStock);
    }
    
    private OnOffStockRecord onStock(Long bookId, Long location, String phone,
    		HttpServletRequest request, BookType bookType){
    	OnOffStockRecord record = new OnOffStockRecord();
        record.setOwnerUserId(getCurrentUserId(request));
    	record.setOwnerPhone(phone);
        record.setBookType(bookType.getType());
    	record.setLocation(location);
    	record.setBookId(bookId);
    	record.setBorrowStatus(BorrowStatus.UNBORROWED.getStatus());
        record = onOffStockRecordService.onStock(record);
    	return record;
    	
    }

    private Long getCurrentUserId(HttpServletRequest request) {
        return (Long) request.getSession().getAttribute(ApplicationConstant.USER_ID);
    }

    @RequestMapping("/book/borrow/{onStockId}")
    public @ResponseBody AjaxResult borrow(@PathVariable(value = "onStockId") Long onStockId, HttpServletRequest request)
                                                                                                                         throws BorrowOrReturnValidationException {
        OnOffStockRecord onOffStockRecord = onOffStockRecordService.findOnOffStockRecordById(onStockId);
        if (null == onOffStockRecord) {
            throw new BorrowOrReturnValidationException(ExceptionCode.InvalidOnOffStockId.getCode());
        } else {
            Integer borrowStatus = onOffStockRecord.getBorrowStatus();
            if (BorrowStatus.UNBORROWED.getStatus() != borrowStatus) {
                throw new BorrowOrReturnValidationException(
                                                            ExceptionCode.TheOnOffStockIdLinkedBookHasBeenBorrowed.getCode());

            }

            BorrowReturnRecord borrowReturnRecord = new BorrowReturnRecord();
            borrowReturnRecord.setBookId(onOffStockRecord.getBookId());
            borrowReturnRecord.setBookType(onOffStockRecord.getBookType());
            borrowReturnRecord.setBorrowStatus(BorrowStatus.BORROWED.getStatus());
            borrowReturnRecord.setBorrowUserId(getCurrentUserId(request));
            borrowReturnRecord.setBorrowDate(DateUtil.getCurrentDate());
            borrowReturnRecord.setOnOffStockId(onOffStockRecord.getId());
            borrowReturnRecord.setOwnerUserId(onOffStockRecord.getOwnerUserId());

        }

        return AjaxResultBuilder.buildSuccessfulResult(null);
    }

}
