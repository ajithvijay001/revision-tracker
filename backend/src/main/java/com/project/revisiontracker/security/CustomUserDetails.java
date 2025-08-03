package com.project.revisiontracker.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetails extends User {
	
	private int userId;
	
	public int getUserId() {
		return userId;
	}
	
	public CustomUserDetails(int userId, String userName, String password, Collection<? extends GrantedAuthority> authorities) {
		super(userName, password, authorities);
		this.userId = userId;
	}

}
