package com.xinran.controller.mobile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xinran.constant.ApplicationConstant;
import com.xinran.pojo.Book;
import com.xinran.pojo.User;
import com.xinran.service.BookService;
import com.xinran.service.UserService;
import com.xinran.vo.AjaxResult;
import com.xinran.vo.HomePageVO;
import com.xinran.vo.builder.AjaxResultBuilder;

@RestController
public class HomePage {

    // private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @RequestMapping("/mobile")
    public @ResponseBody AjaxResult index(HttpServletRequest request) {


        HomePageVO homePageVO = new HomePageVO();

        String accessTokenFromRequest = request.getParameter(ApplicationConstant.ACCESS_TOKEN);
        HttpSession session = request.getSession();
        String accessTokenFromSession = (String) session.getAttribute(ApplicationConstant.ACCESS_TOKEN);
        if (null != accessTokenFromSession && StringUtils.equals(accessTokenFromRequest, accessTokenFromSession)) {
            Long userId = (Long) session.getAttribute(ApplicationConstant.USER_ID);
            User user = userService.findUserByUserId(userId);
            if (null != user) {
                String nickName = user.getNickName();
                if (null != nickName) {
                    homePageVO.setNickName(nickName);
                } else {
                    homePageVO.setNickName("TBD,dear");
                }
            }

        }
        List<Book> bookList = bookService.findAllWithPagenate(10, 0);
        homePageVO.setBookList(bookList);
        return AjaxResultBuilder.buildSuccessfulResult(homePageVO);
    }

}
