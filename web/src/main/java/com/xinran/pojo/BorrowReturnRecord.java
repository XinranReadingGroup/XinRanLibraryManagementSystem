package com.xinran.pojo;

import java.util.Date;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xinran.util.DateUtil;

/**
 * 借还记录 Created by 高海军 帝奇 on 2015 Feb 21 16:45.
 */
@Data
public class BorrowReturnRecord {

    public BorrowReturnRecord() {
        Date now = DateUtil.getCurrentDate();
        this.createdAt = now;
        this.updatedAt = now;
    }

    private Long    id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date    createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date    updatedAt;


    private Long    borrowUserId;
    private Long    ownerUserId;
    private Long    onOffStockId;
    private Long    bookId;
    private int     bookType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date    borrowDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date    returnDate;
    private Integer borrowStatus;

}
