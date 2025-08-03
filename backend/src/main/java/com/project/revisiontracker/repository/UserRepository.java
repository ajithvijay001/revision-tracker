package com.project.revisiontracker.repository;

import org.springframework.stereotype.Repository;

import com.project.revisiontracker.entity.Users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer>{

	Optional<Users> findByUserName(String userName);
	
}
