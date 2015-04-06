package com.xinran.pojo;

import java.util.Date;

import lombok.Data;

/**
 * Created by 高海军 帝奇 on 2015 Feb 21 16:51.
 */
@Data
public class OnOffStockRecord {

    private Long   id;
    private Long   bookId;
    private Long   userId;
    private Date   onStockDate;
    private Date   offStockDate;

}
