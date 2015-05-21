package com.xinran.controller.mobile;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xinran.controller.common.AbstractHomePage;
import com.xinran.service.BookService;
import com.xinran.service.UserService;
import com.xinran.vo.AjaxResult;

@RestController
public class HomePage extends AbstractHomePage {

    // private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @RequestMapping("/mobile")
    public @ResponseBody AjaxResult index(HttpServletRequest request) {

        return super.index(request);
    }

}
