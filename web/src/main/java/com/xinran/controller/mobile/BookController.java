
package com.xinran.controller.mobile;



import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xinran.controller.common.AbstractBookController;
import com.xinran.pojo.Book;
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
    
    
    @RequestMapping("/book/search/{keyword}")
    public @ResponseBody AjaxResult search(@PathVariable(value = "keyword") String keyword, HttpServletRequest request) {
    	List<Book> books = null;
    	Book book = bookService.findBookByISBN(keyword, true);
    	if(book != null){
    		books = new ArrayList<Book>();
    		books.add(book);
    	}else{
    		books = bookService.queryByTitle(keyword);
    	}
    	return AjaxResultBuilder.buildSuccessfulResult(books);
    }


}
