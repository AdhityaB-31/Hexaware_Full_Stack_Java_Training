package com.hexaware.bus_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.bus_service.dto.BusDTO;
import com.hexaware.bus_service.dto.BusPassengerVO;
import com.hexaware.bus_service.entity.Bus;
import com.hexaware.bus_service.service.BusService;

@RestController
@RequestMapping("/api/buses")
public class BusController {
	
	@Autowired
	BusService service;

	@PostMapping(value="/add", produces = "application/json",consumes = "application/json")
	public Bus addCustomer(@RequestBody BusDTO busDTO) {

		return service.addBus(busDTO);
	}

	@GetMapping(value="/get/{busId}" )
	public BusDTO getCustomerById(@PathVariable int busId) {

		return service.getBusById(busId);

	}

	@GetMapping("/getall")
	public List<Bus> getAllBus() {

		return service.getAllBus();

	}

	@GetMapping("/{busId}/passengers")
	public BusPassengerVO getBusWithPassengers(
	        @PathVariable int busId) {

	    return service.getBusWithPassengers(busId);
	}
}
