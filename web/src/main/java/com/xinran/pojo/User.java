package com.xinran.pojo;

import java.util.Date;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xinran.util.DateUtil;

/**
 * Created by 高海军 帝奇 on 2015 Feb 13 07:32.
 */
@Data
public class User {

    public User() {
        Date now = DateUtil.getCurrentDate();
        this.createdAt = now;
        this.updatedAt = now;
    }

    private Long    id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date    createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date    updatedAt;

    private String  userName;
    private String  nickName;           // add
    private String  mobile;
    private String  email;

    private String  password;           // 加密后的密码
    private String  salt;

    private String  resetPasswordToken;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date    resetPasswordSentAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date    rememberCreatedAt;
    private Integer signInCount = 0;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date    currentSignInAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date    lastSignInAt;

    private String  area;               //
    private String  signature;          // 个性签名
    private Integer score;              // 当前用户总积分
    private String  imgId;              // 头像图片id


}
