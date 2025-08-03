package com.project.revisiontracker.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.ResponseEntity.ok;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.revisiontracker.entity.Users;
import com.project.revisiontracker.security.JwtUtil;
import com.project.revisiontracker.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/register")
	public Users registerUser(@RequestBody Users user) {		
		return userService.registerUser(user);
	}
	
	@PostMapping("/login")
	public ResponseEntity<Map<String,String>>loginUser(@RequestBody Users user) {
		System.out.println("User= "+user);
		try {
			Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String token = jwtUtil.generateToken(userDetails);
			return ok(Map.of("Token: ", token));
		}catch(Exception e) {
			
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("Error: ", "Invalid Username or Password"));
		}
	}
}
