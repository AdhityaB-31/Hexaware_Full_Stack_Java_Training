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

import com.hexaware.springJPA.entity.Baggage;
import com.hexaware.springJPA.service.IBaggageService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("app/baggage")
@Log4j2
public class BaggageRestController {

	@Autowired
	IBaggageService service;

	@PostMapping("/add")
	public Baggage addBaggage(@RequestBody Baggage baggage) {
		log.info("Baggage Added successfully");
		return service.addBaggage(baggage);
	}

	@PutMapping("/update")
	public Baggage updateBaggage(@RequestBody Baggage baggage) {
		log.info("Baggage Updated successfully");
		return service.updateBaggage(baggage);
	}

	@GetMapping("/getbyid/{baggageId}")
	public Baggage getBaggageById(@PathVariable int baggageId) {
		log.debug("Fetching baggage by id: " + baggageId);
		return service.getBaggageById(baggageId);
	}

	@GetMapping("/getall")
	public List<Baggage> getAllBaggages() {
		log.info("Fetching all baggages");
		return service.getAllBaggages();
	}

	@DeleteMapping("/deletebyid/{baggageId}")
	public ResponseEntity<String> deleteBaggageById(@PathVariable int baggageId) {
		log.warn("Baggage Record is ready to delete: " + baggageId);
		service.deleteBaggageById(baggageId);
		return new ResponseEntity<String>("Baggage Record Deleted Successfully", HttpStatus.ACCEPTED);
	}

	@GetMapping("/getbytype/{baggageType}")
	public List<Baggage> getBaggagesByType(@PathVariable String baggageType) {
		log.debug("Fetching baggages by type: " + baggageType);
		return service.getBaggagesByType(baggageType);
	}

	@GetMapping("/getbyweightgt/{weightKg}")
	public List<Baggage> getBaggagesByWeightGreaterThan(@PathVariable double weightKg) {
		log.debug("Fetching baggages with weight greater than: " + weightKg);
		return service.getBaggagesByWeightGreaterThan(weightKg);
	}
}