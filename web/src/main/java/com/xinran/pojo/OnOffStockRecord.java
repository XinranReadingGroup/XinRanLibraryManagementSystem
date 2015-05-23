package com.xinran.pojo;

import java.util.Date;

import com.xinran.util.DateUtil;

import lombok.Data;

/**
 * 捐书,享书记录 Created by 高海军 帝奇 on 2015 Feb 21 16:51.
 */
@Data
public class OnOffStockRecord {
	
	public OnOffStockRecord(){
        Date now = DateUtil.getCurrentDate();
		this.createdAt = now;
		this.updatedAt = now;
		this.onStockDate = now;
	}

    private Long    id;
    private Date    createdAt;
    private Date    updatedAt;

    private Long    bookId;
    private Integer bookType;
    private Long    ownerUserId;
    private String  ownerPhone;
    private Long    location;     // TODO using id?

    private Date    onStockDate;
    private Date    offStockDate;

    private Long    borrowId;    // 当前借书记录的Id
    private Long    borrowUserId; // 当前借书人ID
    private Integer borrowStatus;

    
    private Book    book;

}
