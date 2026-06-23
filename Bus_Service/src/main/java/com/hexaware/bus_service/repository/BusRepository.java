package com.hexaware.bus_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hexaware.bus_service.entity.Bus;

public interface BusRepository extends JpaRepository<Bus, Integer> {
	public Bus findByBusId(int busId);
}