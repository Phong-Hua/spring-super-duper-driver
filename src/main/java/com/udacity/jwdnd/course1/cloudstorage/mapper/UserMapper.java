package com.udacity.jwdnd.course1.cloudstorage.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;

@Mapper
public interface UserMapper {

	@Select("SELECT * FROM USERS WHERE username = #{username} LIMIT 1")
	User getUser(String username);
	
	@Insert("INSERT INTO USERS(username, salt, password, firstname, lastname) "
			+ "VALUES (#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
	@Options(useGeneratedKeys = true, keyProperty = "userId")
	int insertUser(User user);
}
