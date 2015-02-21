package com.xinran.pojo;

import java.util.Date;

/**
 * Created by 高海军 帝奇 on 2015 Feb 21 16:45.
 */
public class BorrowReturnRecord {

    private Long   id;
    private Long   bookId;
    private Long   userId;
    private Date   borrowDate;
    private Date   returnDate;
    private String status;
}
