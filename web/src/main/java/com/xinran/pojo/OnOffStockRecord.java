package com.xinran.pojo;

import java.util.Date;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xinran.util.DateUtil;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date    createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date    updatedAt;

    private Long    bookId;
    private Integer bookType;
    private Long    ownerUserId;
    private String  ownerPhone;
    private Long    location;    // TODO using locationId?
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date    onStockDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date    offStockDate;

    private Long    borrowId;    // 当前借书记录的Id
    private Long    borrowUserId; // 当前借书人ID
    private Integer borrowStatus;

    // @JSONField(serialize = false)
    // private Book book;

}
