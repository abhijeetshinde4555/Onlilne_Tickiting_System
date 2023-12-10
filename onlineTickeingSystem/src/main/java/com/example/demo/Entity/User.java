package com.example.demo.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter 
@NoArgsConstructor
@AllArgsConstructor 
public class User {
	
	@Id
	@GeneratedValue ( strategy = GenerationType.AUTO )
	private Integer userId;
	
	private String userName;
	
	private String email;
	
	private String Password; 
	
	private String	phoneNumber;
	
	private String adhaarNumber;
	
	private String role;
	
	private String qrCode;

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", email=" + email + ", Password=" + Password
				+ ", phoneNumber=" + phoneNumber + ", adhaarNumber=" + adhaarNumber + ", role=" + role + ", qrCode="
				+ qrCode + "]";
	}





	
	

	
	
	
	

}
