package com.xinran.dao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.xinran.pojo.User;

public interface UserMapper {

    // TODO add index

	@Select("SELECT * FROM user WHERE id = #{userId}")
    @Result(property = "parentId", column = "c_parent_id")
    public User findUserById(@Param("userId") long userId);

    @Select("SELECT * FROM user WHERE mobile = #{mobile}")
    public User findUserByMobile(@Param("mobile") String mobile);

    @Select("SELECT * FROM user WHERE email = #{email}")
    public User findUserByEmail(@Param("email") String email);

    @Select("SELECT * FROM user WHERE user_Name = #{userName}")
    public User findUserByUserName(@Param("userName") String userName);

    @Insert("insert into user (created_At, updated_At,user_Name, nick_Name,mobile, email,password, salt,reset_Password_Token, reset_Password_Sent_At,remember_Created_At, signIn_Count,"
            + "current_Sign_In_At, last_Sign_In_At,area,signature,score) values(now(),now(),#{userName}, #{nickName},"
            + " #{mobile}, #{email}, #{password}, #{salt}, #{resetPasswordToken}, #{resetPasswordSentAt}, #{rememberCreatedAt}, "
            + "#{signInCount}, #{currentSignInAt}, #{lastSignInAt}, #{area}, #{signature}, #{score})")
    // @Options(useGeneratedKeys = true, keyProperty = "id")
    @Options(useGeneratedKeys = true)
    public void addUser(User user);
    
    @Update("update  user set   updated_At = #{updatedAt},user_Name =  #{userName}, nick_Name = #{nickName},mobile=#{mobile},"
            + " email=#{email},password=#{password}, salt= #{salt},reset_Password_Token =#{resetPasswordToken},"
            + " reset_Password_Sent_At =#{resetPasswordSentAt},remember_Created_At=#{rememberCreatedAt}"
            + ", signIn_Count =#{signInCount},current_Sign_In_At = #{currentSignInAt},"
            + " last_Sign_In_At =#{lastSignInAt},area= #{area},signature=#{signature},score=#{score} where id = #{id}")
 
    public int updateUser(User user);
    



}
