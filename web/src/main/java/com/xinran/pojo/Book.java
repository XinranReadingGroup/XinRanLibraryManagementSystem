package com.xinran.pojo;

import java.util.Date;

import lombok.Data;

/**
 * Created by 高海军 帝奇 on 2015 Feb 15 10:10.
 */

@Data
public class Book {

    private Long    id;
    private Date    createdAt;
    private Date    updatedAt;

    private String  isbn;
    private String  title;
    private String  imgURL;
    private String  author;
    private String  memo;

    private Long    ownerUserId;

    private Integer bookStatus;

}
