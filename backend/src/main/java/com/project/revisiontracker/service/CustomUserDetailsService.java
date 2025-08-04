package com.project.revisiontracker.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.revisiontracker.entity.Users;
import com.project.revisiontracker.repository.UserRepository;
import com.project.revisiontracker.security.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = userRepo.findByUserName(username).orElseThrow(()-> new UsernameNotFoundException("User don't exists"));
		
		return new CustomUserDetails(user.getUserId(), 
				user.getUserName(), 
				user.getPassword(), 
				Collections.singleton(new SimpleGrantedAuthority("USER")));
	}

}
