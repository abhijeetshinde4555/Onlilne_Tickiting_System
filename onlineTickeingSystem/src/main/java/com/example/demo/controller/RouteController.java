package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.payloads.ApiResponse;
import com.example.demo.payloads.RouteDTO;
import com.example.demo.payloads.StopDTO;
import com.example.demo.service.RouteService;
import com.example.demo.service.StopService;

import jakarta.persistence.criteria.Path;
import jakarta.validation.Valid;

@RestController
@RequestMapping ( "/rssc" )  //rssc - route and stop service controller 
public class RouteController {
	
	@Autowired
	private RouteService routeService;
	
	@Autowired
	private StopService stopService;
	
	
	//--------------------Route Service basic CRUD operations-----------
	
	@PostMapping ("/route/")
	public ResponseEntity<RouteDTO> createRoute ( @Valid @RequestBody RouteDTO routeDTO ){
		RouteDTO createdRoute = this.routeService.createRoute(routeDTO);
		return new ResponseEntity<> ( createdRoute , HttpStatus.CREATED );
	}
	
	
	@GetMapping ("/route/{routeId}")
	public ResponseEntity<RouteDTO> getRouteById ( @Valid @PathVariable Integer routeId ){
		RouteDTO route = this.routeService.getRouteById(routeId);
		return new ResponseEntity<> ( route , HttpStatus.OK );
	}
	
	
	@GetMapping ("/route/")
	public ResponseEntity<List<RouteDTO>> getAllRoutes(){
		List<RouteDTO> allRoutes = this.routeService.getAllRoutes();
		return new ResponseEntity<> ( allRoutes , HttpStatus.OK );
	}
	
	
	@PutMapping ("/route/{routeId}")
	public ResponseEntity<RouteDTO> updateRoute ( @Valid @RequestBody RouteDTO routeDTO , @PathVariable Integer routeId ){
		RouteDTO updatedRoute = this.routeService.updateRoutes(routeDTO,routeId );
		return new ResponseEntity<> ( updatedRoute , HttpStatus.OK );
	}
	
	
	@DeleteMapping("/route/{routeId}")
	public ResponseEntity<ApiResponse> deleteRoute ( @Valid @PathVariable Integer routeId ){
		this.routeService.deleteRoute(routeId);
		return new ResponseEntity<> ( new ApiResponse("Route deleted Successfully", true) , HttpStatus.OK );
	}
	
	//---------------------Stop Service Basic CRUD Operations--------------------
	
	
	@PostMapping ("/stop/")
	public ResponseEntity<StopDTO> createStop ( @Valid @RequestBody StopDTO stopDTO ){
		StopDTO createdStop = this.stopService.createStop(stopDTO);
		return new ResponseEntity<> ( createdStop , HttpStatus.CREATED );
	}
	
	
	@GetMapping ("/stop/{stopId}")
	public ResponseEntity<StopDTO> getStopById ( @Valid @PathVariable Integer stopId ){
		StopDTO stop = this.stopService.getStopById(stopId);
		return new ResponseEntity<> ( stop , HttpStatus.OK );
	}
	
	
	@GetMapping("/stop/")
	public ResponseEntity<List<StopDTO>> getAllStop(){
		List<StopDTO> allStops = this.stopService.getAllStops();
		return new ResponseEntity<> ( allStops , HttpStatus.OK );
	}
	
	
	@PutMapping("/stop/{stopId}/{newName}")
	public ResponseEntity<StopDTO> updateStopName ( @Valid @PathVariable String newName , @Valid @PathVariable Integer stopId ){
		StopDTO updatedStop = this.stopService.updateStopName(newName, stopId);
		return new ResponseEntity<> ( updatedStop , HttpStatus.OK );
	}
	
	
	@DeleteMapping("/stop/{stopId}")
	public ResponseEntity<ApiResponse> deleteStop( @Valid @PathVariable Integer stopId ){
		this.stopService.deleteStop(stopId);
		return new ResponseEntity<> ( new ApiResponse( "Stop deleted successfully" , true ) , HttpStatus.OK);
	}
	
	//------------------------Route Service Business logic---------------------------
	
	
	@GetMapping("/route/fare/{startPoint}/{endPoint}/{routeId}")
	public ResponseEntity<Float> calculateFare (@PathVariable String startPoint , @PathVariable String endPoint , @PathVariable Integer routeId ){
		Float calcualtedFare = this.routeService.calculateFare(startPoint, endPoint, routeId);
		return new ResponseEntity<> ( calcualtedFare , HttpStatus.OK );
	}
	
	
	
	@GetMapping("/route/{startPoint}/{endPoint}")
	public ResponseEntity<List<RouteDTO>> findRoutes ( @PathVariable String startPoint , @PathVariable String endPoint ){
		List<RouteDTO> availableRoutes = this.routeService.findRoutes(startPoint, endPoint);
		return new ResponseEntity<> ( availableRoutes , HttpStatus.OK );
		
	}
	
	
	
	
}
