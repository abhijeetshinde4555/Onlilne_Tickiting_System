package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.payloads.UserDTO;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelmapper;

	@Override
	public UserDTO createUser(UserDTO userDto) {
		// TODO Auto-generated method stub
		System.out.println(userDto);
		User user = this.dtoToUser(userDto);
		User savedUser = this.userRepo.save(user);
		System.out.println(savedUser);
		return this.modelmapper.map(savedUser, UserDTO.class);
	}

	@Override
	public UserDTO updateUser(UserDTO userDto, Integer userId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

		user.setUserName(userDto.getUserName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setPhoneNumber(userDto.getPhoneNumber());
		user.setRole(userDto.getRole());
		user.setAdhaarNumber(userDto.getAdhaarNumber());
		user.setQrCode(userDto.getQrCode());

		User updatedUser = this.userRepo.save(user);
		UserDTO userDto1 = this.userToDto(updatedUser);
		return userDto1;
	}
	
	@Override
	public UserDTO getUserById(Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId).
							orElseThrow( () -> new ResourceNotFoundException("User", " Id ", userId) );
		
		return this.userToDto(user);
	}

	@Override
	public List<UserDTO> getAlluser() {
		// TODO Auto-generated method stub
		List<User> users = this.userRepo.findAll();
		List<UserDTO> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		this.userRepo.delete(user);
	}
	
	public User dtoToUser ( UserDTO userDTO ) {
		//User user = this.modelmapper.map(usrDTO, User.class);
		User user = new User();
		user.setUserName(userDTO.getUserName());
		user.setEmail(userDTO.getEmail());
		user.setPhoneNumber(userDTO.getPhoneNumber());
		user.setRole(userDTO.getRole());
		user.setAdhaarNumber(userDTO.getAdhaarNumber());
		return user;
	}
	
	public UserDTO userToDto ( User user ) {
		UserDTO userDTO = this.modelmapper.map(user, UserDTO.class);
		return userDTO;
	}
	
}
