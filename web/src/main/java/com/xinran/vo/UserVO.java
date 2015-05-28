package com.xinran.vo;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 高海军 帝奇 Apr 25, 2015 1:40:55 PM
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserVO extends BasicUserVO {


    private Date    lastSignInAt;

    private String  area;               //
    private String  signature;          // 个性签名
    private Integer score;       // add

}
