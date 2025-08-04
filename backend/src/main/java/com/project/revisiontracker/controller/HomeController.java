package com.project.revisiontracker.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {
	
	@GetMapping("/home")
	@PreAuthorize("isAuthenticated()")
	public String homePage() {
		System.out.println("Home page");
		return "Welcome to HomePage";
	}
}
