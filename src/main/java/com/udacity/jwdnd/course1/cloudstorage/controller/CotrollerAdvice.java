package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.io.FileNotFoundException;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class CotrollerAdvice {

	private final static String resultPage = "result";
	
	/**
	 * Controller Advice to handle max size exception
	 * @param theModel
	 * @param exc
	 * @return
	 */
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ModelAndView handleMaxSizeException(Model theModel, MaxUploadSizeExceededException exc) {
		theModel.addAttribute("error", "Maximum upload size exceeded.");
		return new ModelAndView(resultPage);
	}
	
	@ExceptionHandler(FileNotFoundException.class)
	public ModelAndView handleFileNotFoundException(Model theModel) {
		theModel.addAttribute("error", "File does not exist.");
		return new ModelAndView(resultPage);
	}
}
