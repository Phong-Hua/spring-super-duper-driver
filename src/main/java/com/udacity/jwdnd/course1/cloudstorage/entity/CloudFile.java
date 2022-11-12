package com.udacity.jwdnd.course1.cloudstorage.entity;

import java.util.Arrays;

public class CloudFile {

	private int fileId;
	private String fileName;
	private String contentType;
	private long fileSize;
	private int userId;
	private byte[] fileData;
	
	public CloudFile() {
	}

	public CloudFile(String fileName, String contentType, long filesize, int userId, byte[] fileData) {
		this.fileName = fileName;
		this.contentType = contentType;
		this.fileSize = filesize;
		this.userId = userId;
		this.fileData = fileData;
	}

	public int getFileId() {
		return fileId;
	}

	public void setFileId(int fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long filesize) {
		this.fileSize = filesize;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	@Override
	public String toString() {
		return "CloudFile [fileId=" + fileId + ", fileName=" + fileName + ", contentType=" + contentType + ", filesize="
				+ fileSize + ", userId=" + userId + ", fileData=" + Arrays.toString(fileData) + "]";
	}
}
