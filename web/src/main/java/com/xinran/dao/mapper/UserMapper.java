package com.xinran.dao.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.xinran.pojo.User;

public interface UserMapper {
	@Select("SELECT * FROM user WHERE id = #{userId}")
	public User getUser(long userId);

    @Insert("insert into user (created_At, updated_At,userName, nickName,mobile, email,password, salt,reset_Password_Token, reset_Password_Sent_At,remember_Created_At, signIn_Count,"
            + "current_Sign_In_At, last_Sign_In_At,signature,score) values(now(),now(),#{userName}, #{userName})")
    public int addUser(User user);
    
    
    // private Date createdAt;
    // private Date updatedAt;
    //
    // private String userName;
    // private String nickName; // add
    // private String mobile;
    // private String email;
    //
    // private String password; // 加密后的密码
    // private String salt;
    //
    // private String resetPasswordToken;
    // private Date resetPasswordSentAt;
    // private Date rememberCreatedAt;
    // private Integer signInCount;
    // private Date currentSignInAt;
    // private Date lastSignInAt;
    //
    // private String area; //
    // private String signature; // 个性签名
    // private Integer score; // add

    // id bigint(20) unsigned primary key auto_increment,
    // created_At datetime ,
    // updated_At datetime ,
    // userName varchar(64),
    // nickName varchar(64),
    // mobile varchar(64),
    // email varchar(64),
    // password varchar(64),
    // salt varchar(64),
    // reset_Password_Token varchar(255),
    //
    // reset_Password_Sent_At datetime ,
    // remember_Created_At datetime ,
    // signIn_Count bigint(20) unsigned ,
    //
    // current_Sign_In_At datetime ,
    // last_Sign_In_At datetime ,
    // signature varchar(512),
    // score int


}
