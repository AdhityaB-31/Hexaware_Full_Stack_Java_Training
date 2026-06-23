package com.hexaware.passenger_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.passenger_service.entity.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Integer> {

    List<Passenger> findByBusId(int busId);
    
}