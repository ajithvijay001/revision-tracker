package com.project.revisiontracker.service;

import java.util.Optional;

import com.project.revisiontracker.entity.Users;

public interface UserService {

	public Users registerUser(Users user);

	public Optional<Users> loginUser(Users user);
}
