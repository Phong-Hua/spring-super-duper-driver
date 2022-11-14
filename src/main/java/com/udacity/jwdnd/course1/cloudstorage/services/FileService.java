package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.entity.CloudFile;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;

@Service
public class FileService {

	private FileMapper fileMapper;
	
	public FileService(FileMapper fileMapper) {
		this.fileMapper = fileMapper;
	}
	
	public List<CloudFile> getFiles(int userId) {
		return fileMapper.getFiles(userId);
	}
	
	public int insertFile(CloudFile file) {
		return fileMapper.insertFile(file); 
	}
	
	public CloudFile getFile(int fileId, int userId) {
		return fileMapper.getFile(fileId, userId);
	}
	
	public int deleteFile(int fileId, int userId) {
		return fileMapper.deleteFile(fileId, userId);
	}
}
