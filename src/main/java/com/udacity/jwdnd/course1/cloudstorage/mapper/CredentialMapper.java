package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.entity.CloudCredential;

@Mapper
public interface CredentialMapper {

	@Select("SELECT * FROM CREDENTIALS WHERE userid=#{userId}")
	@Results({
		@Result(property = "encryptedPassword", column = "password")
	})	// mapped the column 'password' to 'encryptedPassword' property in CloudCredential
	List<CloudCredential> getCredentials(int userId);
	
	@Insert("INSERT INTO CREDENTIALS(url, encodedkey, username, password, userid) VALUES(#{url}, #{encodedKey}, #{username}, #{encryptedPassword}, #{userId})")
	@Options(useGeneratedKeys = true, keyProperty = "credentialId")
	int insertCredential(CloudCredential credential);
	
	@Update("UPDATE CREDENTIALS SET url=#{url}, encodedKey=#{encodedKey}, username=#{username}, password=#{encryptedPassword} WHERE credentialid=#{credentialId} AND userid=#{userId}")
	int updateCredential(CloudCredential credential);
	
	@Delete("DELETE FROM CREDENTIALS WHERE credentialid=#{credentialId} AND userid=#{userId}")
	int deleteCredential(int credentialId, int userId);
}
