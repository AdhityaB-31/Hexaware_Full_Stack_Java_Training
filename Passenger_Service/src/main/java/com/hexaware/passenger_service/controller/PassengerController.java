package com.hexaware.passenger_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.hexaware.passenger_service.dto.PassengerDTO;
import com.hexaware.passenger_service.entity.Passenger;
import com.hexaware.passenger_service.service.PassengerService;

@RestController
@RequestMapping("/api/passengers")
@CrossOrigin("*")
public class PassengerController {

	@Autowired
	private PassengerService passengerService;

	@PostMapping("/add")
	public Passenger addPassenger(@RequestBody PassengerDTO passengerDTO) {

		return passengerService.addPassenger(passengerDTO);
	}

	@PutMapping("/update")
	public Passenger updatePassenger(@RequestBody PassengerDTO passengerDTO) {

		return passengerService.updatePassenger(passengerDTO);
	}

	@GetMapping("/all")
	public List<Passenger> getAllPassengers() {

		return passengerService.getAll();
	}

	@GetMapping("/bus/{busId}")
	public List<Passenger> getPassengersByBusId(@PathVariable int busId) {

		return passengerService.findByBusId(busId);
	}
}