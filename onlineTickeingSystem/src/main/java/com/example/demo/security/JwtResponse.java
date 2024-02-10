package com.example.demo.security;

import com.example.demo.payloads.UserDTO;

import lombok.Data;

@Data
public class JwtResponse {
	
	private String token;
	
	private UserDTO user;
	
}
