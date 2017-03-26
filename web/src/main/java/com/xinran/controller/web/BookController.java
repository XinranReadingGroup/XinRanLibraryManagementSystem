
package com.xinran.controller.web;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.xinran.controller.common.AbstractBookController;
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
        BookDetail bookDetail = super.buildBookDetailById(id);
        return new ModelAndView("bookDetail", "bookDetail", bookDetail);
    }
    
    @RequestMapping("/book/search")
    public ModelAndView   search(@RequestParam("q") String  query,
                                           HttpServletRequest request) {
      List<BookDetail> bookDetailList = super.buildSearchResult(query);

      return new ModelAndView("bookSearchResultList", "bookSearchResultList", bookDetailList);
    }
}
