
package com.xinran.controller.mobile;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xinran.controller.common.AbstractBookController;
import com.xinran.pojo.Book;
import com.xinran.vo.AjaxResult;
import com.xinran.vo.builder.AjaxResultBuilder;

/**
 * 
 * @author zhuangyao.zy
 *
 */
@RestController
@RequestMapping("/mobile")
public class BookController extends AbstractBookController {

    @RequestMapping("/book/isbn/{isbn}")
    public @ResponseBody AjaxResult getBookByISBN(@PathVariable(value = "isbn") String isbn, HttpServletRequest request) {
        Book book = bookService.findBookByISBN(isbn);
        return AjaxResultBuilder.buildSuccessfulResult(book);
    }
}
