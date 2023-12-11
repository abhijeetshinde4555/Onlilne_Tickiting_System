package com.example.demo.service;

import java.util.List;

import com.example.demo.payloads.StopDTO;

public interface StopService {
	
	StopDTO createStop ( StopDTO stopDTO );
	
	StopDTO getStopById ( Integer stopId );
	
	List<StopDTO> getAllStops();
	
	StopDTO updateStopName( String newName , Integer stopId );
	
	void deleteStop( Integer stopId );
	
}
