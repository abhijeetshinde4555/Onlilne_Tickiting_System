package com.example.demo.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.authentication.AuthenticationManagerBeanDefinitionParser;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Entity.User;
import com.example.demo.payloads.UserDTO;
import com.example.demo.repo.UserRepo;
import com.example.demo.security.JwtHelper;
import com.example.demo.security.JwtRequest;
import com.example.demo.security.JwtResponse;
import com.example.demo.security.JwtTokenHelper_Copy;
import com.example.demo.service.UserService;

import io.jsonwebtoken.io.IOException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
   		private Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	  	@Autowired
	    private UserDetailsService userDetailsService;
	  	
	  	@Autowired
	  	private UserService userService;
 
	  	//@Autowired
	  	private final AuthenticationManager authenticationManager;
	    
	    @Autowired
	    private JwtHelper helper;
	    
	    @Autowired
	    private UserRepo userRepo;
	    
	    @Autowired
	    private ModelMapper modelMapper;
	    
	    @Autowired
	    public AuthController(AuthenticationManager authenticationManager) {
	        this.authenticationManager = authenticationManager;
	    }


	    @PostMapping("/login")
	    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) throws AuthenticationException, Exception {

	        this.doAuthenticate(request.getEmail(), request.getPassword());


	        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
	        String token = helper.generateToken(userDetails.getUsername());

	        JwtResponse response = new JwtResponse();
	        
	        response.setToken(token);
	        response.setUser(this.modelMapper.map( (User)userDetails , UserDTO.class));
	        
	        return new ResponseEntity<>(response, HttpStatus.OK);
	    }
	    
	    
		@PostMapping("/register")
		public ResponseEntity<?> registerUser(@Valid @RequestBody UserDTO userDTO) throws Exception {
			
			User user = this.modelMapper.map(userDTO , User.class);
			
			if ( userRepo.findByEmail(user.getEmail()).isPresent() ) {
				throw new Exception("User Already Registered");
			}
			
			UserDTO registeredUser = this.userService.registerNewUser(userDTO);
			return new ResponseEntity<UserDTO>(registeredUser, HttpStatus.CREATED);
			
		}

	    
	    private void doAuthenticate(String email, String password) throws AuthenticationException, Exception {

	        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
	        try {
	            authenticationManager.authenticate(authentication);

	        } catch (BadCredentialsException e) {
	            throw new BadCredentialsException(" Invalid Username or Password  !!");
	        }

	    }

	    @ExceptionHandler(BadCredentialsException.class)
	    public String exceptionHandler() {
	        return "Credentials Invalid !!";
	    }
	    
	    
	    @PostMapping("/authenticate")
	    public void createAuthenticationToken ( @RequestBody JwtRequest authenticationRequest ) throws IOException{
	    	try {
	    		authenticationManager.authenticate(
	    				new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), 
	    						authenticationRequest.getPassword()));
	    	}
	    	catch (BadCredentialsException e) {
				throw new BadCredentialsException("Incorrect Username or Password");
			}
	    	
	    	final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
	    	final String jwtToken = helper.generateToken(userDetails.getUsername());
	    	
	    	
	    }
}

