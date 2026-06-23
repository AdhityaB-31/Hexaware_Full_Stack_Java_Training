package com.hexaware.passenger_service.service;

import java.util.List;

import com.hexaware.passenger_service.dto.PassengerDTO;
import com.hexaware.passenger_service.entity.Passenger;

public interface PassengerService {
	
	public Passenger addPassenger(PassengerDTO passengerDTO);
	public Passenger updatePassenger(PassengerDTO passengerDTO);
	public List<Passenger> getAll();
	public List<Passenger> findByBusId(int busId);

}
