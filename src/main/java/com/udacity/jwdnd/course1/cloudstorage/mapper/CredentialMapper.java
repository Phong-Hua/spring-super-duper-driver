package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.entity.CloudCredential;

@Mapper
public interface CredentialMapper {

	@Select("SELECT * FROM CREDENTIALS")
	List<CloudCredential> getCredentials();
	
	@Insert("INSERT INTO CREDENTIALS(url, encodedkey, username, password, userid) VALUES(#{url}, #{encodedKey}, #{username}, #{password}, #{userId})")
	@Options(useGeneratedKeys = true, keyProperty = "credentialId")
	int insertCredential(CloudCredential credential);
}
