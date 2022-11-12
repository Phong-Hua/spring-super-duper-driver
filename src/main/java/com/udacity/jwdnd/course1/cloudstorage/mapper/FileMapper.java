package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.entity.CloudFile;

@Mapper
public interface FileMapper {

	@Select("SELECT * FROM FILES")
	List<CloudFile> getFiles();
	
	@Insert("INSERT INTO FILES(filename, contenttype, filesize, filedata, userid) VALUES(#{fileName}, #{contentType}, #{fileSize}, #{fileData}, #{userId})")
	@Options(useGeneratedKeys = true, keyProperty = "fileId")
	int insertFile(CloudFile file);
}
