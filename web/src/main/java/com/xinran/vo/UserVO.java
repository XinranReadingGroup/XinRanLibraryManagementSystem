package com.xinran.vo;

import java.util.Date;

import lombok.Data;

/**
 * @author 高海军 帝奇 Apr 25, 2015 1:40:55 PM
 */
@Data
public class UserVO {


    private String  nickName;           // add

    private Date    lastSignInAt;

    private String  area;               //
    private String  signature;          // 个性签名
    private Integer score;       // add
    private String  imgId;              // 头像图片id

}
