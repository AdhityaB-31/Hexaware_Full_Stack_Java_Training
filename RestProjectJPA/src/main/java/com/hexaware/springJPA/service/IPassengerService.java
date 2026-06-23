package com.hexaware.springJPA.service;

import java.util.List;

import com.hexaware.springJPA.PassengerDTO;
import com.hexaware.springJPA.entity.*;

public interface IPassengerService {

	public Passenger addPassenger(PassengerDTO dto);

	public Passenger updatePassenger(PassengerDTO dto);

	public PassengerDTO getByPassengerid(int passengerId);

	public void deleteByPassengerid(int passengerId);

	public List<Passenger> getAllPassenger();

	public Passenger findByPassengerName(String passengerName);

	public List<Passenger> findByAgeGreaterThan(int age);
	
	public List<Passenger> findByPassengerNameLike(String passengerName);
	
	public List<Passenger> getAllSortedByAge(int age);
	
	public void updatePassengerAge(int passengerId, int age);
	
	public List<Passenger> getAllAgeLT(int age);

	public void updateName(int passengerId,String passengerName);
	
	public List<Passenger> findByPassengerIdGreaterThan(int passengerId);
	
	public List<Passenger> findByPassengerIdBetween(int initial,int fin);

}
