package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.entity.CloudCredential;
import com.udacity.jwdnd.course1.cloudstorage.entity.CloudFile;
import com.udacity.jwdnd.course1.cloudstorage.entity.CloudNote;
import com.udacity.jwdnd.course1.cloudstorage.entity.DeleteItem;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/home")
public class HomeController {

	private NoteService noteService;
	private UserService userService;
	private CredentialService credentialService;
	private FileService fileService;
	
	public HomeController(NoteService noteService, UserService userService, 
			CredentialService credentialService, FileService fileService) {
		this.noteService = noteService;
		this.userService = userService;
		this.credentialService = credentialService;
		this.fileService = fileService;
	}

	@GetMapping
	public String showHome(Authentication authentication, Model theModel) {
		
		List<CloudNote> notes = noteService.getNotes();
		List<CloudCredential> credentials = credentialService.getCredentials();
		List<CloudFile> files = fileService.getFiles();

		theModel.addAttribute("notes", notes);
		theModel.addAttribute("credentials", credentials);
		theModel.addAttribute("files", files);
		
		return "home";
	}
	
	@PostMapping("/notes")
	public String uploadNote(Authentication authentication, CloudNote note, Model theModel) {

		int userId = userService.getUserId(authentication.getName());
		if (userId > 0) {
			note.setUserId(userId);
			int noteId = noteService.addOrUpdate(note);
			if (noteId > 0) {
				theModel.addAttribute("success", "Note is added successfully.");
			} else {
				theModel.addAttribute("error", "Note failed to added. Try again.");
			}
		} else {
			theModel.addAttribute("error", "User is not authenticated.");
		}
		return "result";
	}
	
	@PostMapping("/delete")
	public String deleteItem(Authentication authentication, DeleteItem item, Model theModel) {
		String error = null;
		if (item.getItemType().equals("note")) {
			int result = noteService.deleteNote(item.getItemId());
			if (result == 0) {
				error = "Note failed to be deleted. Try again.";
			}
		} else if (item.getItemType().equals("credential")) {
			int result = credentialService.deleteCredential(item.getItemId());
			if (result == 0) {
				error = "Note failed to be deleted. Try again.";
			}
		}
		
		if (error != null) {
			theModel.addAttribute("error", error);
			return "result";
		}
		
		return "redirect:/home";
	}
	
	@PostMapping("/credentials")
	public String addCredential(Authentication authentication, CloudCredential credential, Model theModel) {

		// get the user id
		int userId = userService.getUserId(authentication.getName());
		
		if (userId > 0) {
			credential.setUserId(userId);
			int result = credentialService.addOrUpdateCredential(credential);
			if (result <= 0) {
				theModel.addAttribute("error", "Credential failed to added. Try again.");
			} else {
				theModel.addAttribute("success", "Credential is added successfully.");
			}
			
		} else {
			theModel.addAttribute("error", "User is not authenticated.");
		}
		
		return "result";
	}
	
	@PostMapping("/files")
	public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile file, Model theModel) {

		int userId = userService.getUserId(authentication.getName());
		if (userId > 0) {
			try {

				CloudFile cloudFile = new CloudFile(file.getOriginalFilename(), file.getContentType(), 
						file.getSize(), userId, file.getBytes());
				int result = fileService.insertFile(cloudFile);
				if (result <= 0) {
					theModel.addAttribute("error", "File failed to added. Try again.");
				} else {
					theModel.addAttribute("success", "File added successfully.");
				}
			} catch( IOException e) {
				theModel.addAttribute("error", "File does not exist. Try again.");
			}
			
			
		} else {
			theModel.addAttribute("error", "User is not authenticated.");
		}
		return "result";
	}
}
