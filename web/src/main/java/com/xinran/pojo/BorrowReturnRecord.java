package com.xinran.pojo;

import java.util.Date;

import lombok.Data;

import com.xinran.constant.BorrowStatus;
import com.xinran.constant.DonateStatus;

/**
 * Created by 高海军 帝奇 on 2015 Feb 21 16:45.
 */
@Data
public class BorrowReturnRecord {

    private Long         id;
    private Date         createdAt;
    private Date         updatedAt;

    private Long         bookId;
    private DonateStatus bookDonateStatus; // 冗余

    private Long         userId;
    private Date         borrowDate;
    private Date         returnDate;
    private BorrowStatus borrowStatus;

}
