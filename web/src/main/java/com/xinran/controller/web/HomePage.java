package com.xinran.controller.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.xinran.controller.common.AbstractHomePage;
import com.xinran.service.BookService;
import com.xinran.service.UserService;
import com.xinran.vo.HomePageVO;

@RestController(value = "webHomePage")
public class HomePage extends AbstractHomePage {

    // private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public ModelAndView indexOfHomePage(HttpServletRequest request) {
        HomePageVO homepage = super.buildHomePageVO(request);
        return new ModelAndView("homePage", "homepageVO", homepage);

    }

}
