package com.xinran.pojo;

import java.util.Date;

import lombok.Data;

/**
 * Created by 高海军 帝奇 on 2015 Feb 21 16:51.
 */
@Data
public class OnOffStockRecord {

    private Long    id;
    private Date    createdAt;
    private Date    updatedAt;

    private Long    bookId;
    private Integer bookStatus;  // 冗余的
    private Long    ownerUserId;

    private Date    onStockDate;
    private Date    offStockDate;

    private Integer borrowStatus;

}
