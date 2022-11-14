package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.entity.CloudNote;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;

@Service
public class NoteService {

	private NoteMapper noteMapper;

	public NoteService(NoteMapper noteMapper) {
		this.noteMapper = noteMapper;
	}
	
	public List<CloudNote> getNotes(int userId) {
		return noteMapper.getNotes(userId);
	}
	
	public int addOrUpdate(CloudNote note) {
		if (note.getNoteId() > 0) {
			return noteMapper.updateNote(note);
		} else {
			return noteMapper.insertNote(note);
		}
	}
	
	public int deleteNote(int noteId, int userId) {
		return noteMapper.deleteNote(noteId, userId);
	}
}
