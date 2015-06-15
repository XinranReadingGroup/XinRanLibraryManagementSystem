
package com.xinran.controller.web;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.xinran.controller.common.AbstractBookController;
import com.xinran.pojo.Book;
import com.xinran.vo.BookDetail;

/**
 * 
 * @author zhuangyao.zy
 *
 */
@RestController(value = "webBookController")
public class BookController extends AbstractBookController {

    // @RequestMapping("/book/isbn/{isbn}")
    // public ModelAndView getBookByISBN(@PathVariable(value = "isbn") String isbn, HttpServletRequest request) {
    // Book book = bookService.findBookByISBN(isbn);
    // return new ModelAndView("bookDetail", "bookDetail", book);
    // }

    @RequestMapping("/book/share/new")
    public ModelAndView shareNewBook(HttpServletRequest request) {
        return new ModelAndView("shareNewBook");
    }

    @RequestMapping("/book/donate/new")
    public ModelAndView donateNewBook(HttpServletRequest request) {
        return new ModelAndView("donateNewBook");
    }

    @RequestMapping("/book/detail/{id}")
    public ModelAndView getBookById(@PathVariable(value = "id") Long id, HttpServletRequest request) {
        BookDetail bookDetail = super.buildBookDetail(id);
        return new ModelAndView("bookDetail", "bookDetail", bookDetail);
    }
}
