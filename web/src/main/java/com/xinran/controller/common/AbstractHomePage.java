package com.xinran.controller.common;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.xinran.controller.util.UserIdenetityUtil;
import com.xinran.pojo.Book;
import com.xinran.pojo.OnOffStockRecord;
import com.xinran.pojo.Pagination;
import com.xinran.pojo.User;
import com.xinran.service.BookService;
import com.xinran.service.OnOffStockRecordService;
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
    
    @Autowired
    protected OnOffStockRecordService   onOffStockRecordService;

    public @ResponseBody AjaxResult index(HttpServletRequest request) {

        HomePageVO homePageVO = buildHomePageVO(request);
        return AjaxResultBuilder.buildSuccessfulResult(homePageVO);
    }

    protected HomePageVO buildHomePageVO(HttpServletRequest request) {
        HomePageVO homePageVO = new HomePageVO();

         
        Long userId = UserIdenetityUtil.getCurrentUserId(request);
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

        
        Pagination  page = new Pagination();
        List<OnOffStockRecord>  onOffStockRecordList =   onOffStockRecordService.findOnlyOnStockRecordList(page);
        
    	List<Book> bookList = Lists.newArrayListWithExpectedSize(20);

        for (OnOffStockRecord onOffStockRecord : onOffStockRecordList) {
        	Long bookId = onOffStockRecord.getBookId();
        	Book book = 	bookService.findBookById(bookId);
        	
        	//TODO MAGIC 重新设置了Id,后面最好抽成独立的VO
        	book.setId(onOffStockRecord.getId());
        	bookList.add(book);
		}
        
        homePageVO.setBookList(bookList);
        return homePageVO;
    }

}
