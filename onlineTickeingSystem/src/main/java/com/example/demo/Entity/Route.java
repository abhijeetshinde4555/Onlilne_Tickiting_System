package com.example.demo.Entity;

import java.util.Map;

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
public class Route {
	
	@Id
	@GeneratedValue ( strategy = GenerationType.AUTO )
	private Integer routeId;
	
	private String routeName;
	private String description;
	private Map<Stops , Float> stopsAndRates;
	
	
}
