package com.example.demo.payloads;

import java.util.Map;

import com.example.demo.Entity.Stops;

public class RouteDTO {
	
	private Integer routeId;
	
	private String routeName;
	
	private String description;
	
	private Map<StopDTO , Float> stopsAndRates;
}
