package com.xinran.pojo;

import java.util.Date;

import lombok.Data;

/**
 * 借还记录 Created by 高海军 帝奇 on 2015 Feb 21 16:45.
 */
@Data
public class BorrowReturnRecord {

    private Long    id;
    private Date    createdAt;
    private Date    updatedAt;

    private Long    borrowUserId;
    private Long    ownerUserId;
    private Long    bookId;
    private int     bookType;



    private Date    borrowDate;
    private Date    returnDate;
    private Integer borrowStatus;

}
