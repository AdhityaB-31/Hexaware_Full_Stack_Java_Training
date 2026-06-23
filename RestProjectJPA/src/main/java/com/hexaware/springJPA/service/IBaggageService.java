package com.hexaware.springJPA.service;

import java.util.List;

import com.hexaware.springJPA.entity.Baggage;

public interface IBaggageService {

	public Baggage addBaggage(Baggage baggage);

	public Baggage updateBaggage(Baggage baggage);

	public Baggage getBaggageById(int baggageId);

	public void deleteBaggageById(int baggageId);

	public List<Baggage> getAllBaggages();

	public List<Baggage> getBaggagesByType(String baggageType);

	public List<Baggage> getBaggagesByWeightGreaterThan(double weightKg);
}