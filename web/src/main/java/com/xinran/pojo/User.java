package com.xinran.pojo;

import lombok.Data;

import java.util.Date;

/**
 * Created by 高海军 帝奇 on 2015 Feb 13 07:32.
 */
@Data
public class User {

    private Long    id;
    private String  userName;
    private String  nickName;           // add

    private String  password;           // 加密后的密码
    private String  salt;
    private String  mobile;
    private String  email;

    private String  resetPasswordToken;
    private Date    resetPasswordSentAt;
    private Date    rememberCreatedAt;
    private Integer signInCount;
    private Date    currentSignInAt;
    private Date    lastSignInAt;

    private String  area;               //
    private String  signatur;           // 个性签名
    private Integer score;              // add

}
