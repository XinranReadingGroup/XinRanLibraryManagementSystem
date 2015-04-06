package com.xinran.pojo;

import com.xinran.constant.BorrowStatus;
import com.xinran.constant.DonateStatus;

import lombok.Data;

/**
 * Created by 高海军 帝奇 on 2015 Feb 15 10:10.
 */

@Data
public class Book {

    private Integer      id;
    private String       isbn;
    private String       title;
    private String       imgURL;
    private String       desc;
    private String       author;
    private DonateStatus donateState;
    private BorrowStatus borrowStatus;
    private Long         userId;

}
