package com.hexaware.springJPA.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hexaware.springJPA.PassengerDTO;
import com.hexaware.springJPA.entity.Passenger;
import com.hexaware.springJPA.repository.PassengerRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Transactional
@Log4j2
public class PassengerServiceImp implements IPassengerService {

	@Autowired
	PassengerRepository repo;

	@Override
	public Passenger addPassenger(PassengerDTO dto) {
		Passenger pr = new Passenger();

		pr.setPassengerId(dto.getPassengerId());
		pr.setPassengerName(dto.getName());
		pr.setAge(dto.getAge());
		pr.setGender(dto.getGender());

		return repo.save(pr);
	}

	@Override
	public Passenger updatePassenger(PassengerDTO dto) {

		Passenger pr = new Passenger();

		pr.setPassengerId(dto.getPassengerId());
		pr.setPassengerName(dto.getName());
		pr.setAge(dto.getAge());
		pr.setGender(dto.getGender());
		
		return repo.save(pr);

	}

	@Override
	public PassengerDTO getByPassengerid(int passengerId) {
		
		
		Passenger pr = repo.findById(passengerId).orElse(null);
		

		PassengerDTO dto = new PassengerDTO();
		dto.setPassengerId(pr.getPassengerId());
		dto.setName(pr.getPassengerName());
		dto.setAge(pr.getAge());
		dto.setGender(pr.getGender());
		
		log.info("");

		return dto;

	}

	@Override
	public void deleteByPassengerid(int passengerId) {
		// TODO Auto-generated method stub
		repo.deleteById(passengerId);
	}

	@Override
	public List<Passenger> getAllPassenger() {

		return repo.findAll();
	}

	@Override
	public Passenger findByPassengerName(String passengerName) {

		return repo.findByPassengerName(passengerName);
	}

	@Override
	public List<Passenger> findByAgeGreaterThan(int age) {

		return repo.findByAgeGreaterThan(age);
	}

	@Override
	public List<Passenger> findByPassengerNameLike(String passengerName) {
		
		return repo.findByPassengerNameContaining(passengerName);
	}

	@Override
	public List<Passenger> getAllSortedByAge(int age) {
		
		return repo.getAllSortedByAge(age);
	}

	@Override
	public void updatePassengerAge(int passengerId, int age) {
		
		repo.updatePassengerAge(passengerId, age);
		
	}

	@Override
	public List<Passenger> getAllAgeLT(int age) {
		
		return repo.getAllAgeLT(age);
	}

	@Override
	public void updateName(int passengerId, String passengerName) {
		log.info("Updated request is transferred to Repository Class...");
		
		repo.updateName(passengerId, passengerName);
		
		log.info("Update request is performed and values are changed....");
		
	}

	@Override
	public List<Passenger> findByPassengerIdGreaterThan(int passengerId) {
		
		return repo.findByPassengerIdGreaterThan(passengerId);
	}

	@Override
	public List<Passenger> findByPassengerIdBetween(int initial, int fin) {
		return repo.findByPassengerIdBetween(initial, fin);
	}

}
