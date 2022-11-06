package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;

@Controller
@RequestMapping("/signup")
public class SignupController {

	private UserService userService;
	
	private SignupController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public String showSignupForm() {
		return "signup";
	}
	
	@PostMapping
	public String processSignupForm(User theUser, Model theModel) {
		
		String errorMsg = null;
		
		boolean usernameAvailable = userService.isUsernameAvailable(theUser.getUsername());
		
		if (usernameAvailable) {
			int id = userService.createUser(theUser);
			if (id <= 0) {
				errorMsg = "An error has happened. Try again.";
			} else {
				theModel.addAttribute("signupSuccess", true);
			}
		} else {
			errorMsg = "Username already exists.";
		}
		
		if (errorMsg != null)
			theModel.addAttribute("errorMsg", errorMsg);
		
		return "signup";
	}
}
