package com.xinran.pojo;

import java.util.Date;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xinran.util.DateUtil;

/**
 * Created by 高海军 帝奇 on 2015 Feb 15 10:10.
 */

@Data
public class Book {

    public Book() {
        Date now = DateUtil.getCurrentDate();
        this.createdAt = now;
        this.updatedAt = now;
    }

    private Long   id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date   createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date   updatedAt;

    private String isbn;
    private String title;
    private String imgUrl;
    private String author;
    private String summary;
    private String price;
    private String publisher;

}
