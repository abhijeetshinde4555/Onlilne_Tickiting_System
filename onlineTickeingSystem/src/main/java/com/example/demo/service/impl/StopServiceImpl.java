package com.example.demo.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Stops;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.payloads.StopDTO;
import com.example.demo.repo.StopRepo;
import com.example.demo.service.StopService;

@Service
public class StopServiceImpl implements StopService {
	
	@Autowired
	private StopRepo stopRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public StopDTO createStop(StopDTO stopDTO) {
		// TODO Auto-generated method stub
		Stops stop = this.modelMapper.map(stopDTO, Stops.class);	
		Stops savedStop = this.stopRepo.save(stop);
		return this.modelMapper.map(savedStop, StopDTO.class);
	}

	@Override
	public StopDTO getStopById(Integer stopId) {
		// TODO Auto-generated method stub
		Stops stop = this.stopRepo.findById(stopId).
								orElseThrow( () -> new ResourceNotFoundException( "Stop","stopId", stopId  ));  
		return this.modelMapper.map(stop, StopDTO.class);
	}

	@Override
	public List<StopDTO> getAllStops() {
		// TODO Auto-generated method stub
		List<Stops> stopList = this.stopRepo.findAll();
		List<StopDTO> stopDToList = stopList.stream()
								.map( stop -> this.stopToDTO(stop) )
								.collect(Collectors.toList());
		return stopDToList;
	}

	@Override
	public StopDTO updateStopName(String newName, Integer stopId) {
		// TODO Auto-generated method stub
		Stops stop = this.stopRepo.findById(stopId).
				orElseThrow( () -> new ResourceNotFoundException( "Stop","stopId", stopId  ));
		stop.setStopName(newName);
		Stops updatedStop = this.stopRepo.save(stop);
		return this.modelMapper.map( updatedStop , StopDTO.class );
	}

	@Override
	public void deleteStop(Integer stopId) {
		// TODO Auto-generated method stub
		Stops stop = this.stopRepo.findById(stopId).
				orElseThrow( () -> new ResourceNotFoundException( "Stop","stopId", stopId  ));
		this.stopRepo.delete(stop);
	}
	
	public StopDTO stopToDTO ( Stops stop ) {
		StopDTO stopDTO = this.modelMapper.map( stop , StopDTO.class );
		return stopDTO;
	}
	
	public Stops DTOtoStop ( StopDTO stopDTO ) {
		Stops stop = this.modelMapper.map(stopDTO, Stops.class);
		return stop;
	}
	
	
}
