package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Stops;

public interface StopRepo extends JpaRepository<Stops, Integer> {

}
