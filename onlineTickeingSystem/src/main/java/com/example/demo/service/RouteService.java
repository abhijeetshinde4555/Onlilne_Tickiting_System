package com.example.demo.service;

import java.util.List;

import com.example.demo.payloads.RouteDTO;
import com.example.demo.payloads.StopDTO;

public interface RouteService {
	
	RouteDTO createRoute ( RouteDTO routeDTO );
	
	RouteDTO getRouteById ( Integer routeId );
	
	List<RouteDTO> getAllRoutes();
	
	RouteDTO updateRoutes ( RouteDTO routeDto , Integer routeId);
	
	void deleteRoute ( Integer routeId );
	
	List<RouteDTO> findRoutes ( String startPoint , String endPoint );
	
	Float calculateFare ( String startPoint , String endPoint , Integer routeId );
	
	StopDTO searchStop ( String keyword );
	
}
