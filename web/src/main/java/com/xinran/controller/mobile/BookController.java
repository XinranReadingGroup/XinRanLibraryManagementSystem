
package com.xinran.controller.mobile;



import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.xinran.constant.BorrowStatus;
import com.xinran.constant.SystemResultCode;
import com.xinran.controller.util.UserIdenetityUtil;
import com.xinran.exception.BorrowOrReturnValidationException;
import com.xinran.exception.XinranCheckedException;
import com.xinran.pojo.OnOffStockRecord;
import com.xinran.pojo.QRCode;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xinran.controller.common.AbstractBookController;
import com.xinran.vo.AjaxResult;
import com.xinran.vo.BookDetail;
import com.xinran.vo.builder.AjaxResultBuilder;

/**
 * 
 * @author zhuangyao.zy
 *
 */
@RestController
@RequestMapping("/mobile")
public class BookController extends AbstractBookController {

    // @RequestMapping("/book/isbn/{isbn}")
    // public @ResponseBody AjaxResult getBookByISBN(@PathVariable(value = "isbn") String isbn, HttpServletRequest
    // request) {
    // Book book = bookService.findBookByISBN(isbn);
    // return AjaxResultBuilder.buildSuccessfulResult(book);
    // }

    @RequestMapping("/book/detail/{id}")
    public @ResponseBody AjaxResult getBookById(@PathVariable(value = "id") Long id, HttpServletRequest request) {
        BookDetail bookDetail = super.buildBookDetail(id);
        return AjaxResultBuilder.buildSuccessfulResult(bookDetail);
    }


    @RequestMapping("/book/qrCode/{qrCode}")
    public @ResponseBody AjaxResult getBookDetailByQrCode(@PathVariable(value = "qrCode") String qrCodeContent, HttpServletRequest request)
            throws BorrowOrReturnValidationException {

        try {

            QRCode qrCode =  qrCodeService.findQRCodeByContent(qrCodeContent);
            if(qrCode == null){
                throw new XinranCheckedException(SystemResultCode.NoPreAssignedQrCodeFound);
            }
            OnOffStockRecord onOffStockRecord = onOffStockRecordService.findOnOffStockRecordByQRCodeId(qrCode.getId());
            if (null == onOffStockRecord) {
                throw new BorrowOrReturnValidationException(SystemResultCode.InvalidOnOffStockId);
            } else {

                BookDetail bookDetail = super.buildBookDetail(onOffStockRecord);
                return AjaxResultBuilder.buildSuccessfulResult(bookDetail);
            }

        }catch (XinranCheckedException e){
            return AjaxResultBuilder.buildFailedResult(e);

        }

    }




//    @RequestMapping("/book/search/{keyword}")
//    public @ResponseBody AjaxResult search(@PathVariable(value = "keyword") String keyword, HttpServletRequest request) {
//    	List<Book> books = null;
//    	Book book = bookService.findBookByISBN(keyword, true);
//    	if(book != null){
//    		books = new ArrayList<Book>();
//    		books.add(book);
//    	}else{
//    		books = bookService.queryByTitle(keyword);
//    	}
//    	return AjaxResultBuilder.buildSuccessfulResult(books);
//    }
    
    @RequestMapping("/book/search")
    public @ResponseBody AjaxResult search(@RequestParam("q") String  query,
                                           HttpServletRequest request) {
      List<BookDetail> bookDetailList = super.buildSearchResult(query);

        return AjaxResultBuilder.buildSuccessfulResult(bookDetailList);
    }


}
