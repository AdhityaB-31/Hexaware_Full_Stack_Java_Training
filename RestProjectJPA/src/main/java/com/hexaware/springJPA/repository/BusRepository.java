package com.hexaware.springJPA.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.springJPA.entity.Bus;

@Repository
public interface BusRepository extends JpaRepository<Bus, Integer> {

	public List<Bus> findByOriginAndDestination(String origin, String destination);

	public List<Bus> findByTotalSeatsGreaterThan(int seats);
}