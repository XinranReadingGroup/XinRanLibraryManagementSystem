package com.xinran.vo;

import java.util.List;

import lombok.Data;

import com.xinran.pojo.Book;

/**
 * @author 高海军 帝奇 Apr 11, 2015 4:16:40 PM
 */
@Data
public class HomePageVO {

    private String     nickName;
    private boolean    isLogined;

    private List<Book> bookList;
}
