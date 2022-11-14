package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.entity.CloudNote;

@Mapper
public interface NoteMapper {

	@Select("SELECT * FROM NOTES WHERE userid=#{userId}")
	List<CloudNote> getNotes(int userId);
	
	@Insert("INSERT INTO NOTES(notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
	@Options(useGeneratedKeys = true, keyProperty = "noteId")
	int insertNote(CloudNote note);
	
	@Update("UPDATE NOTES SET notetitle=#{noteTitle}, notedescription=#{noteDescription} WHERE noteid = #{noteId} AND userid=#{userId}")
	int updateNote(CloudNote note);
	
	@Delete("DELETE FROM NOTES WHERE noteid = #{noteId} AND userid=#{userId}")
	int deleteNote(int noteId, int userId);
}
