package com.xinran.dao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.xinran.pojo.User;

public interface UserMapper {


	@Select("SELECT * FROM user WHERE id = #{userId}")
      User findUserById(@Param("userId") Long userId);

//    @Select("SELECT * FROM user WHERE mobile = #{mobile}")
//    public User findUserByMobile(@Param("mobile") String mobile);

    @Select("SELECT * FROM user WHERE email = #{email}")
      User findUserByEmail(@Param("email") String email);

    @Select("SELECT * FROM user WHERE user_Name = #{userName}")
      User findUserByUserName(@Param("userName") String userName);

    @Insert("insert into user (created_At, updated_At,user_Name, nick_Name,mobile, email,password, salt,reset_Password_Token, reset_Password_Sent_At,remember_Created_At, signIn_Count,"
            + "current_Sign_In_At, last_Sign_In_At,area,signature,img_Id,score) values(now(),now(),#{userName}, #{nickName},"
            + " #{mobile}, #{email}, #{password}, #{salt}, #{resetPasswordToken}, #{resetPasswordSentAt}, #{rememberCreatedAt}, "
            + "#{signInCount}, #{currentSignInAt}, #{lastSignInAt}, #{area}, #{signature},#{imgId}, #{score})")
    // @Options(useGeneratedKeys = true, keyProperty = "id")
    @Options(useGeneratedKeys = true)
      void addUser(User user);
    
    @Update("update  user set   updated_At = #{updatedAt},user_Name =  #{userName}, nick_Name = #{nickName},mobile=#{mobile},"
            + " email=#{email},password=#{password}, salt= #{salt},reset_Password_Token =#{resetPasswordToken},"
            + " reset_Password_Sent_At =#{resetPasswordSentAt},remember_Created_At=#{rememberCreatedAt}"
            + ", signIn_Count =#{signInCount},current_Sign_In_At = #{currentSignInAt},"
            + " last_Sign_In_At =#{lastSignInAt},area= #{area},signature=#{signature},img_Id=#{imgId},score=#{score} where id = #{id}")
 
      int updateUser(User user);
    



}
