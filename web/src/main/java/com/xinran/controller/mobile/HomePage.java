package com.xinran.controller.mobile;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xinran.pojo.Book;
import com.xinran.service.BookService;

@RestController
public class HomePage {

    private Logger      log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookService bookService;

    @RequestMapping("/")
    public String greeting() {
        List<Book> xx = bookService.findAllWithPagenate(10, 0);
        log.warn("book query result" + xx);
        return "homePage";
    }

}
