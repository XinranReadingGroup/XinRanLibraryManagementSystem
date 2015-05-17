package com.xinran.controller.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinran.constant.ApplicationConstant;
import com.xinran.pojo.Book;
import com.xinran.pojo.User;
import com.xinran.service.BookService;
import com.xinran.service.UserService;
import com.xinran.vo.AjaxResult;
import com.xinran.vo.HomePageVO;
import com.xinran.vo.builder.AjaxResultBuilder;

public class AbstractHomePage {

    // private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    public @ResponseBody AjaxResult index(HttpServletRequest request) {

        HomePageVO homePageVO = buildHomePageVO(request);
        return AjaxResultBuilder.buildSuccessfulResult(homePageVO);
    }

    protected HomePageVO buildHomePageVO(HttpServletRequest request) {
        HomePageVO homePageVO = new HomePageVO();

        // String accessTokenFromRequest = request.getParameter(ApplicationConstant.ACCESS_TOKEN);
        HttpSession session = request.getSession();
        // String accessTokenFromSession = (String) session.getAttribute(ApplicationConstant.ACCESS_TOKEN);
        // if (null != accessTokenFromSession && StringUtils.equals(accessTokenFromRequest, accessTokenFromSession)) {
        Long userId = (Long) session.getAttribute(ApplicationConstant.USER_ID);
        if (null != userId) {
            homePageVO.setLogined(true);
            User user = userService.findUserByUserId(userId);
            if (null != user) {
                String nickName = user.getNickName();
                if (null != nickName) {
                    homePageVO.setNickName(nickName);
                } else {
                    homePageVO.setNickName("TBD");
                }
            }
        }

        // }
        List<Book> bookList = bookService.findAllWithPagenate(10, 0);
        homePageVO.setBookList(bookList);
        return homePageVO;
    }

}
