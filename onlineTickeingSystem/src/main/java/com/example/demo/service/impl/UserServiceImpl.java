package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
import com.example.demo.config.AppConstants;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.payloads.UserDTO;
import com.example.demo.repo.RoleRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelmapper;
	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
	
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDTO registerNewUser(UserDTO userDTO)  {
			
			
			User user = this.modelmapper.map(userDTO , User.class);
			// encoded the password
			user.setPassword(this.passwordEncoder().encode(user.getPassword()));
			// roles
			if ( user.getRoles().equals("User") ) {
				Role role = this.roleRepo.findById(AppConstants.NORMAL).get();
				user.getRoles().add(role);
			}
			else if ( ( user.getRoles().equals("Conductor") ) ){
				Role role = this.roleRepo.findById(AppConstants.CONDUCTOR).get();
				user.getRoles().add(role);
			}
			else {
				Role role = this.roleRepo.findById(AppConstants.NORMAL).get();
				user.getRoles().add(role);
			}
			User newUser = this.userRepo.save(user);
			return this.modelmapper.map(newUser, UserDTO.class);
	}

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
		user.setPassword(userDTO.getPassword());
		user.setPhoneNumber(userDTO.getPhoneNumber());
		user.setAdhaarNumber(userDTO.getAdhaarNumber());
		return user;
	}
	
	public UserDTO userToDto ( User user ) {
		UserDTO userDTO = this.modelmapper.map(user, UserDTO.class);
		return userDTO;
	}

	
}
