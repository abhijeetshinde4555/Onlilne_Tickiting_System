package com.example.demo.service;

import java.util.List;

import com.example.demo.payloads.UserDTO;

public interface UserService {
	
	UserDTO createUser ( UserDTO userDto );
	
	UserDTO getUserById ( Integer userId );
	
	List<UserDTO> getAlluser ();
	
	UserDTO updateUser(UserDTO userDto, Integer userId);
	 
	void deleteUser ( Integer userId );
	
}
