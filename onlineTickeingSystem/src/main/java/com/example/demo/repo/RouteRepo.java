package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Route;

public interface RouteRepo extends JpaRepository<Route, Integer> {

}
