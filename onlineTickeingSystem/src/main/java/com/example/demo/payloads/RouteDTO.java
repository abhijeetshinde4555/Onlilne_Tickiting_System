package com.example.demo.payloads;

import java.util.LinkedHashMap;
import java.util.Map;

import com.example.demo.Entity.Stops;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter 
@NoArgsConstructor
@AllArgsConstructor
public class RouteDTO {
	
	private Integer routeId;
	 
	private String routeName;
	
	private String description;
	
	private Map<String , Float> stopsAndRates;
}
