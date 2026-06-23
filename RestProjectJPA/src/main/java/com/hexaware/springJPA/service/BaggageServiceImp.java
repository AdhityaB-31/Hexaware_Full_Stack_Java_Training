package com.hexaware.springJPA.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.springJPA.entity.Baggage;
import com.hexaware.springJPA.repository.BaggageRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
public class BaggageServiceImp implements IBaggageService {

	@Autowired
	BaggageRepository repo;

	@Override
	public Baggage addBaggage(Baggage baggage) {
		log.info("Adding baggage: " + baggage.getBaggageId());
		return repo.save(baggage);
	}

	@Override
	public Baggage updateBaggage(Baggage baggage) {
		log.info("Updating baggage: " + baggage.getBaggageId());
		return repo.save(baggage);
	}

	@Override
	public Baggage getBaggageById(int baggageId) {
		log.info("Fetching baggage by id: " + baggageId);
		return repo.findById(baggageId).orElse(null);
	}

	@Override
	public void deleteBaggageById(int baggageId) {
		log.warn("Deleting baggage with id: " + baggageId);
		repo.deleteById(baggageId);
	}

	@Override
	public List<Baggage> getAllBaggages() {
		log.info("Fetching all baggages");
		return repo.findAll();
	}

	@Override
	public List<Baggage> getBaggagesByType(String baggageType) {
		log.info("Fetching baggages by type: " + baggageType);
		return repo.findByBaggageType(baggageType);
	}

	@Override
	public List<Baggage> getBaggagesByWeightGreaterThan(double weightKg) {
		log.info("Fetching baggages with weight greater than: " + weightKg);
		return repo.findByWeightKgGreaterThan(weightKg);
	}
}