package com.xinran.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.xinran.pojo.User;

public interface UserMapper {
	@Select("SELECT * FROM user WHERE id = #{userId}")
	public User getUser(long userId);

    @Insert("insert into user (email, userName) values(#{email}, #{userName})")
    public int addUser(User user);
}
