package com.example.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import com.example.demo.entities.Users;

public interface UsersRepository
                         extends 
                         JpaRepositoryImplementation<Users, Integer>{
	
	public Users findByEmail(String email);

}
