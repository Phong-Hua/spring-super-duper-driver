package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.entity.CloudNote;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/home")
public class HomeController {

	private NoteService noteService;
	private UserService userService;
	
	public HomeController(NoteService noteService, UserService userService) {
		this.noteService = noteService;
		this.userService = userService;
	}

	@GetMapping
	public String showHome(Authentication authentication, Model theModel) {
		
		List<CloudNote> notes = noteService.getNotes();
		theModel.addAttribute("notes", notes);
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
}
