package com.hexaware.springJPA.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.springJPA.entity.Bus;
import com.hexaware.springJPA.repository.BusRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
public class BusServiceImp implements IBusService {

	@Autowired
	BusRepository repo;

	@Override
	public Bus addBus(Bus bus) {
		log.info("Adding Bus: " + bus.getBusNumber());
		return repo.save(bus);
	}

	@Override
	public Bus updateBus(Bus Bus) {
		log.info("Updating Bus: " + Bus.getBusId());
		return repo.save(Bus);
	}

	@Override
	public Bus getBusById(int BusId) {
		log.info("Fetching Bus by id: " + BusId);
		return repo.findById(BusId).orElse(null);
	}

	@Override
	public void deleteBusById(int BusId) {
		log.warn("Deleting Bus with id: " + BusId);
		repo.deleteById(BusId);
	}

	@Override
	public List<Bus> getAllBus() {
		log.info("Fetching all Buss");
		return repo.findAll();
	}

	@Override
	public List<Bus> getBussByOriginAndDestination(String origin, String destination) {
		log.info("Fetching Buss from " + origin + " to " + destination);
		return repo.findByOriginAndDestination(origin, destination);
	}

	@Override
	public List<Bus> getBussBySeatsGreaterThan(int seats) {
		log.info("Fetching Buss with seats greater than: " + seats);
		return repo.findByTotalSeatsGreaterThan(seats);
	}
}