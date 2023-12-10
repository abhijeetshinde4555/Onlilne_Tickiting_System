package com.example.demo.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
	
	private Integer userId;
	
	@NotBlank ( message = "UserName can not be empty" )
	private String userName;
	
	@NotBlank ( message = "email Id can not be empty" )
	@Email
	private String email;
	
	@NotBlank ( message = "password can not be empty" )
	@Pattern ( regexp = "^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$" , 
				message = "Invalid Password, Please Enter Valid Password" )
	private String Password;
	
	@NotBlank ( message = "phone Number can not be empty" )
	private String	phoneNumber;
	
	@NotBlank ( message = "adhaar number can not be empty" )
	private String adhaarNumber;
	
	private String role;
	
	private String qrCode;

	@Override
	public String toString() {
		return "UserDTO [userId=" + userId + ", userName=" + userName + ", email=" + email + ", Password=" + Password
				+ ", phoneNumber=" + phoneNumber + ", adhaarNumber=" + adhaarNumber + ", role=" + role + ", qrCode="
				+ qrCode + "]";
	}



	
	

	
}
