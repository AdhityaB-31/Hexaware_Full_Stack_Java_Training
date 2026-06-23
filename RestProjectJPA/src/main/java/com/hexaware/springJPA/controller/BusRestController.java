package com.hexaware.springJPA.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.springJPA.entity.Bus;
import com.hexaware.springJPA.service.IBusService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("app/Bus")
@Log4j2
public class BusRestController {

	@Autowired
	IBusService service;

	@PostMapping("/add")
	public Bus addBus(@RequestBody Bus Bus) {
		log.info("Bus Added successfully");
		return service.addBus(Bus);
	}

	@PutMapping("/update")
	public Bus updateBus(@RequestBody Bus Bus) {
		log.info("Bus Updated successfully");
		return service.updateBus(Bus);
	}

	@GetMapping("/getbyid/{BusId}")
	public Bus getBusById(@PathVariable int BusId) {
		log.debug("Fetching Bus by id: " + BusId);
		return service.getBusById(BusId);
	}

	@GetMapping("/getall")
	public List<Bus> getAllBuss() {
		log.info("Fetching all Buss");
		return service.getAllBus();
	}

	@DeleteMapping("/deletebyid/{BusId}")
	public ResponseEntity<String> deleteBusById(@PathVariable int BusId) {
		log.warn("Bus Record is ready to delete: " + BusId);
		service.deleteBusById(BusId);
		return new ResponseEntity<String>("Bus Record Deleted Successfully", HttpStatus.ACCEPTED);
	}

	@GetMapping("/getbyroute/{origin}/{destination}")
	public List<Bus> getBussByRoute(@PathVariable String origin, @PathVariable String destination) {
		log.debug("Fetching Buss from " + origin + " to " + destination);
		return service.getBussByOriginAndDestination(origin, destination);
	}

	@GetMapping("/getbyseatsgt/{seats}")
	public List<Bus> getBussBySeatsGreaterThan(@PathVariable int seats) {
		log.debug("Fetching Buss with seats greater than: " + seats);
		return service.getBussBySeatsGreaterThan(seats);
	}
}