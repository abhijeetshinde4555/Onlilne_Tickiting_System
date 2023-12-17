package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Route;
import com.example.demo.Entity.Stops;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.payloads.RouteDTO;
import com.example.demo.payloads.StopDTO;
import com.example.demo.repo.RouteRepo;
import com.example.demo.service.RouteService;
import com.example.demo.service.StopService;

@Service
public class RouteServiceImpl implements RouteService {
	
	@Autowired 
	private StopService stopService;
	
	@Autowired
	private RouteRepo routeRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public RouteDTO createRoute(RouteDTO routeDTO) {
		// TODO Auto-generated method stub
		Route route = this.modelMapper.map(routeDTO, Route.class);
		Route createdRoute = this.routeRepo.save(route);
		return this.modelMapper.map(createdRoute, RouteDTO.class);
	}

	@Override
	public RouteDTO getRouteById(Integer routeId) {
		// TODO Auto-generated method stub
		Route route = this.routeRepo.findById(routeId)
							.orElseThrow( () -> new ResourceNotFoundException("Route", "Id", routeId) );
		return this.modelMapper.map(route, RouteDTO.class);
	}

	@Override
	public List<RouteDTO> getAllRoutes() {
		// TODO Auto-generated method stub
		List<Route> routes = this.routeRepo.findAll();
		List<RouteDTO> routesDTO = routes.stream()
										.map( route -> this.modelMapper.map(route, RouteDTO.class) )
										.collect( Collectors.toList() );
		return routesDTO;
	}

	@Override
	public RouteDTO updateRoutes( RouteDTO routeDto , Integer routeId ) {
		// TODO Auto-generated method stub
		Route route = this.routeRepo.findById(routeId)
										.orElseThrow( () -> new ResourceNotFoundException("Route","Id",routeId) );
		route.setRouteName(routeDto.getRouteName());
		route.setDescription(routeDto.getDescription());
		route.setStopsAndRates(routeDto.getStopsAndRates());
		
		Route updatedRoute = this.routeRepo.save(route);
		
		return this.modelMapper.map(updatedRoute, RouteDTO.class);
	}

	@Override
	public void deleteRoute(Integer routeId) {
		// TODO Auto-generated method stub
		Route route = this.routeRepo.findById(routeId)
						.orElseThrow( () -> new ResourceNotFoundException("Route","Id",routeId) );
		this.routeRepo.delete(route);
	}

	@Override
	public List<RouteDTO> findRoutes(String startPoint, String endPoint) {
		
		// TODO Auto-generated method stub
		List<RouteDTO> allRoutes = this.getAllRoutes();
		List<RouteDTO> availaleRoutes = new ArrayList<>();
		for ( RouteDTO route : allRoutes ) {
			if ( route.getStopsAndRates().containsKey(startPoint) && route.getStopsAndRates().containsKey(endPoint) ) {
				availaleRoutes.add(route);
			}
		}
		if ( availaleRoutes.isEmpty() ) {
			throw new ResourceNotFoundException("Route", "provided stops ", startPoint+" "+endPoint);
		}
		return availaleRoutes;
	}  

	@Override
	public Float calculateFare(String startPoint, String endPoint , Integer routeId ) {
		// TODO Auto-generated method stub
		Route route = this.routeRepo.findById(routeId)
						.orElseThrow( () -> new ResourceNotFoundException( "Route", "Id" , routeId) );
		Map<String, Float> stopsAndRates = route.getStopsAndRates();
		Set<String> stopList = stopsAndRates.keySet();
		
		Float startFare = 0.0f;
		Float endFare = 0.0f;
		
		if ( stopsAndRates.containsKey(startPoint) ) {
			startFare = stopsAndRates.get(startPoint);
		}
		else {
			new ResourceNotFoundException("Stop", "start point", startPoint);
		}
		
		if ( stopsAndRates.containsKey(endPoint) ) {
			endFare = stopsAndRates.get(endPoint);
		}
		else {
			new ResourceNotFoundException("Stop", "end point",endPoint);
		}
		
		System.out.println( "Start Fare : " + startFare + " , End Fare : " + endFare );
		
		Float result = endFare - startFare;

		return result;
	} 
	 

	@Override
	public StopDTO searchStop(String keyword) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
}
