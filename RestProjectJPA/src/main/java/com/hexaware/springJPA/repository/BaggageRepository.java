package com.hexaware.springJPA.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.springJPA.entity.Baggage;

@Repository
public interface BaggageRepository extends JpaRepository<Baggage, Integer> {

	public List<Baggage> findByBaggageType(String baggageType);

	public List<Baggage> findByWeightKgGreaterThan(double weightKg);
}