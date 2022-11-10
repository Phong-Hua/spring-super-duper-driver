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
	
	public List<CloudNote> getNotes() {
		return noteMapper.getNotes();
	}
	
	public int insertNote(CloudNote note) {
		return noteMapper.insertNote(note);
	}
}
