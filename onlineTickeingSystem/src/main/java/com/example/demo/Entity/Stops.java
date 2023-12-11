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
public class Stops {
	
	@Id 
	@GeneratedValue ( strategy = GenerationType.AUTO )
	private Integer stopId;
	private String stopName;
	
	@Override
	public String toString() {
		return "Stops [stopId=" + stopId + ", stopName=" + stopName + "]";
	}
	
	
}
