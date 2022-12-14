package com.udacity.jwdnd.course1.cloudstorage.entity;

public class CloudNote {

	private int noteId;
	private String noteTitle;
	private String noteDescription;
	private int userId;
	
	public CloudNote() {
	}

	public CloudNote(String title, String description, int userId) {
		this.noteTitle = title;
		this.noteDescription = description;
		this.userId = userId;
	}
	
	public int getNoteId() {
		return noteId;
	}

	public void setNoteId(int noteId) {
		this.noteId = noteId;
	}
	
	public String getNoteTitle() {
		return noteTitle;
	}

	public void setNoteTitle(String noteTitle) {
		this.noteTitle = noteTitle;
	}

	public String getNoteDescription() {
		return noteDescription;
	}

	public void setNoteDescription(String noteDescription) {
		this.noteDescription = noteDescription;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "CloudNote [noteId=" + noteId + ", noteTitle=" + noteTitle + ", noteDescription=" + noteDescription
				+ ", userId=" + userId + "]";
	}
}
