package com.project.revisiontracker.service.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.revisiontracker.entity.Users;
import com.project.revisiontracker.exceptions.UsernameAlreadyExistsException;
import com.project.revisiontracker.repository.UserRepository;
import com.project.revisiontracker.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepo;
	
	
	@Override
	public Users registerUser(Users user) {
		Optional<Users> existingUser=userRepo.findByUserName(user.getUserName());
		if( !existingUser.isEmpty()) {
			throw new UsernameAlreadyExistsException(user.getUserName());
		}
		else 
			user.setPassword(new BCryptPasswordEncoder(12).encode(user.getPassword()));
		 return userRepo.save(user);
		
	}

	@Override
	public Optional<Users> loginUser(Users user) {
		return userRepo.findByUserName(user.getUserName());
	}

}
