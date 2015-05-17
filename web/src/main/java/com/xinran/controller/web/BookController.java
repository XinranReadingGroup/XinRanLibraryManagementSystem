
package com.xinran.controller.web;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.xinran.controller.common.AbstractBookController;
import com.xinran.pojo.Book;

/**
 * 
 * @author zhuangyao.zy
 *
 */
@RestController(value = "webBookController")
public class BookController extends AbstractBookController {

    @RequestMapping("/book/isbn/{isbn}")
    public ModelAndView getBookByISBN(@PathVariable(value = "isbn") String isbn, HttpServletRequest request) {
        Book book = bookService.findBookByISBN(isbn);
        return new ModelAndView("bookDetail", "bookDetail", book);

    }
}
